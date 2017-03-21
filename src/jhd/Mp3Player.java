package jhd;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Mp3Player {
	//
	//private String filename;
	private Player player;
	private boolean playing = false;
	private String fileName=null;

	public Mp3Player() {
	}
	public Mp3Player(String fileName){
		this.fileName=fileName;
		
	}
	public void play() {
		if (playing) {
			System.out.println("playing");
			return;
		}
		playing = true;
		try {
			//如果初始指定fileName就播放fileName的MP3
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName==null?"contra.mp3":fileName));
			player = new Player(in);
			System.out.println("play");
			//StatusBar.currentStatus.setText("playing");
			player.play();
			in.close();
			player.close();
			
			// player.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ErrOutput.err("mp3文件未找到");
		} catch (JavaLayerException e) {
			e.printStackTrace();
			ErrOutput.err("mp3播放错误");
		} catch (IOException e) {
			e.printStackTrace();
			ErrOutput.err("IO错误");
		}
		playing = false;

	}

	public void stop() {
		if (player != null) {
			player.close();
			System.out.println("close");
		}
	}

}
