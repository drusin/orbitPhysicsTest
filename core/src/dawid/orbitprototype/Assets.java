package dawid.orbitprototype;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;

@Getter
public class Assets {

	private final Music titleTrack;
	private final Array<Music> songs;

	public Assets() {
		titleTrack = Gdx.audio.newMusic(Gdx.files.internal("music/dreamy.ogg"));
		Music levelMusic2 = Gdx.audio.newMusic(Gdx.files.internal("music/bells.ogg"));
		Music levelMusic3 = Gdx.audio.newMusic(Gdx.files.internal("music/spaaace.ogg"));
		songs = new Array<>();
		songs.add(titleTrack);
		songs.add(levelMusic2);
		songs.add(levelMusic3);
	}

	public void dispose() {
		titleTrack.dispose();
		for (Music m : songs) {
			m.dispose();
		}
	}
}
