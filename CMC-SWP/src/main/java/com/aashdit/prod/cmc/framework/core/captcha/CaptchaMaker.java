package com.aashdit.prod.cmc.framework.core.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.github.cage.Cage;
import com.github.cage.image.EffectConfig;

public class CaptchaMaker extends Cage {

	private MyPainter painter;
	
	public CaptchaMaker() {
		super();
		
		EffectConfig effectConfig = new EffectConfig(false, false, false, true, null);
		this.painter = new MyPainter(MyPainter.DEFAULT_WIDTH, MyPainter.DEFAULT_HEIGHT, Color.WHITE, 
										MyPainter.Quality.MAX, effectConfig, new Random());
	}
	
	@Override
	public BufferedImage drawImage(String text) {

		Font font =new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		
		return painter.draw( font, Color.BLACK, text);
	}

	
	
	
}
