package cz.skylights.spitt;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GameGLView extends GLSurfaceView{
  private GameLevel _level; //GLSurfaceView potrebuje Render, je odvozeny od renderu
	
  public GameGLView(Context context)
  {
	  super(context);
	  _level = new GameLevel();
	  this.setRenderer(_level);
  }
  
  public void loadLevelXML()
  {
	  try {
		_level.loadLevelXML();
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}
