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

	public Mp3Player() {
	}
	public void play() {
		if (playing) {
			System.out.println("playing");
			return;
		}
		playing = true;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream("contra.mp3"));
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
