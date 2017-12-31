package thirdparty.sshj;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.common.LoggerFactory;
import net.schmizz.sshj.common.StreamCopier;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.forwarded.RemotePortForwarder;
import net.schmizz.sshj.connection.channel.forwarded.SocketForwardingConnectListener;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.ConsoleKnownHostsVerifier;
import net.schmizz.sshj.transport.verification.OpenSSHKnownHosts;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SshJSample1 {

  public static void main(String... args)
          throws IOException {
    SshJSample1 sample = new SshJSample1();
    sample.sshjSample();

  }

  public void sshjSample() throws IOException {
    final SSHClient ssh = sshConnectSetting("192.168.11.15", "arimura", "arimura");
    try {
      // コマンド実行
      sessionCommand(ssh.startSession(), "sleep 3600; echo 'hello sshj' >> hello_sshj.txt", 5);
      scpDownloadSample(ssh, "hello_sshj.txt", "./tmp");
      scpUpload(ssh, new FileSystemFile("./tmp/hello_sshj.txt"), "/tmp");

      // ターミナルの起動
      // samplePTY("192.168.11.15", "arimura", "arimura");
      // ExecutorService exec = Executors.newSingleThreadExecutor();
      // ポートフォワード
      // exec.submit(new LocalPortForwarderSample(ssh, "0.0.0.0", 8080, "192.168.11.15", 8080));

    } finally {
      ssh.disconnect();
    }
  }

  // ssh接続の設定
  private SSHClient sshConnectSetting(String host, String username, String password) throws IOException {
    SSHClient ssh = new SSHClient();
    ssh.loadKnownHosts();
    ssh.connect(host);
    // keepAliveの感覚設定
    ssh.getConnection().getKeepAlive().setKeepAliveInterval(5);
    // 接続時のsshキーの指定はauthPublickeyを認証時のパスワードにはauthPasswordを使う

    ssh.authPassword(username,password);
    return ssh;
  }

  private SSHClient sshKeyConnectSetting(String host, String sshKeyFilePath, String username) throws IOException {
    SSHClient ssh = new SSHClient();
    ssh.loadKnownHosts();
    ssh.connect(host);
    // keepAliveの感覚設定
    ssh.getConnection().getKeepAlive().setKeepAliveInterval(5);
    // 接続時のsshキーの指定はauthPublickeyを認証時のパスワードにはauthPasswordを使う
    KeyProvider keys = ssh.loadKeys(new File(sshKeyFilePath).getPath(), "");
    ssh.authPublickey(username,keys);
    return ssh;
  }


  private void sessionCommand(Session session, String command, int timeout) throws IOException {
    try {
      final Session.Command cmd = session.exec(command);
      System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
      cmd.join(timeout, TimeUnit.SECONDS);
      System.out.println("\n** exit status: " + cmd.getExitStatus());
    } finally {
      session.close();
    }
  }

  private void scpDownloadSample(SSHClient ssh, String downloadFilePath, String saveFileDir) throws IOException{
    ssh.newSCPFileTransfer().download(downloadFilePath, new FileSystemFile(saveFileDir));
  }

  private void scpUpload(SSHClient ssh, FileSystemFile uploadFile, String uploadPath) throws IOException{
    ssh.newSCPFileTransfer().upload(uploadFile, uploadPath);
  }

  private void sftpUploadSample(SSHClient ssh, FileSystemFile uploadFile, String uploadDir) throws IOException{
    final SFTPClient sftp = ssh.newSFTPClient();
    try {
      sftp.put(uploadFile, uploadDir);
    } finally {
      sftp.close();
    }
  }

  private void sftpUploadSample(SSHClient ssh, String downloadFilePath, String saveFileDir) throws IOException{
    final SFTPClient sftp = ssh.newSFTPClient();
    try {
      sftp.get(downloadFilePath, new FileSystemFile(saveFileDir));
    } finally {
      sftp.close();
    }
  }

  private void localPortFowardSample(SSHClient ssh, String localHost, int localPort, String remoteHost, int remotePort) throws IOException{
    final LocalPortForwarder.Parameters params
            = new LocalPortForwarder.Parameters(localHost, localPort, remoteHost, remotePort);
    final ServerSocket ss = new ServerSocket();
    ss.setReuseAddress(true);
    ss.bind(new InetSocketAddress(params.getLocalHost(), params.getLocalPort()));
    try {
      ssh.newLocalPortForwarder(params, ss).listen();
    } finally {
      ss.close();
    }
  }

  private void remotePortFowardSample(SSHClient ssh, String remoteHost, int remotePort) throws IOException{
    try {
      ssh.getRemotePortForwarder().bind(
              new RemotePortForwarder.Forward(remotePort),
              new SocketForwardingConnectListener(new InetSocketAddress(remoteHost, remotePort)));

      ssh.getTransport().setHeartbeatInterval(30);

      // Something to hang on to so that the forwarding stays
      ssh.getTransport().join();
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  /**
   * ssh接続してターミナルを起動
   * @throws IOException
   */
  private void samplePTY(String host, String username, String password) throws IOException{
    final SSHClient ssh = new SSHClient();

    final File khFile = new File(OpenSSHKnownHosts.detectSSHDir(), "known_hosts");
    ssh.addHostKeyVerifier(new ConsoleKnownHostsVerifier(khFile, System.console()));

    ssh.connect(host);
    ssh.authPassword(username,password);
    try {

      // ssh.authPublickey(System.getProperty("user.name"));
      final Session session = ssh.startSession();
      try {

        session.allocateDefaultPTY();

        final Session.Shell shell = session.startShell();

        new StreamCopier(shell.getInputStream(), System.out, LoggerFactory.DEFAULT)
                .bufSize(shell.getLocalMaxPacketSize())
                .spawn("stdout");

        new StreamCopier(shell.getErrorStream(), System.err, LoggerFactory.DEFAULT)
                .bufSize(shell.getLocalMaxPacketSize())
                .spawn("stderr");

        // Now make System.in act as stdin. To exit, hit Ctrl+D (since that results in an EOF on System.in)
        // This is kinda messy because java only allows console input after you hit return
        // But this is just an example... a GUI app could implement a proper PTY
        new StreamCopier(System.in, shell.getOutputStream(), LoggerFactory.DEFAULT)
                .bufSize(shell.getRemoteMaxPacketSize())
                .copy();

      } finally {
        session.close();
      }

    } finally {
      ssh.disconnect();
    }
  }

  class LocalPortForwarderSample implements Runnable {
    SSHClient ssh;
    String localHost;
    int localPort;
    String remoteHost;
    int remotePort;

    public LocalPortForwarderSample(SSHClient ssh, String localHost, int localPort,
                                    String remoteHost, int remotePort){
      this.ssh = ssh;
      this.localHost = localHost;
      this.localPort = localPort;
      this.remoteHost = remoteHost;
      this.remotePort = remotePort;

    }
    @Override
    public void run() {
      try{
        final LocalPortForwarder.Parameters params
                = new LocalPortForwarder.Parameters(localHost, localPort, remoteHost, remotePort);
        final ServerSocket ss = new ServerSocket();
        ss.setReuseAddress(true);
        ss.bind(new InetSocketAddress(params.getLocalHost(), params.getLocalPort()));
        try {
          ssh.newLocalPortForwarder(params, ss).listen();
        } finally {
          ss.close();
        }
      }catch (IOException e){
        e.printStackTrace();
      }
    }
  }

  class RemotePortForwarderSample implements Runnable {
    SSHClient client;
    String remoteHost;
    int remotePort;

    public RemotePortForwarderSample(SSHClient ssh, String remoteHost, int remotePort){
      this.client = ssh;
      this.remoteHost = remoteHost;
      this.remotePort = remotePort;
    }

    @Override
    public void run(){

      try {
        client.getRemotePortForwarder().bind(
                new RemotePortForwarder.Forward(remotePort),
                new SocketForwardingConnectListener(new InetSocketAddress(remoteHost, remotePort)));

        client.getTransport().setHeartbeatInterval(30);

        // Something to hang on to so that the forwarding stays
        client.getTransport().join();
      }catch (IOException e){
        e.printStackTrace();
      }
    }
  }
}
