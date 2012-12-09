package com.ufc.scramble_world.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class EasyModeGameActivity extends Activity {
	protected ImageButton[] levels = new ImageButton[20];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_easy_mode_game);
		setMainLayout();
	}

	protected void setMainLayout() {
		Button bt_level_mode = (Button) findViewById(R.id.bt_back_level_mode);
		bt_level_mode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});

		levels[0] = (ImageButton) findViewById(R.id.ib_lvl_1);
		levels[1] = (ImageButton) findViewById(R.id.ib_lvl_2);
		levels[2] = (ImageButton) findViewById(R.id.ib_lvl_3);
		levels[3] = (ImageButton) findViewById(R.id.ib_lvl_4);
		levels[4] = (ImageButton) findViewById(R.id.ib_lvl_5);
		levels[5] = (ImageButton) findViewById(R.id.ib_lvl_6);
		levels[6] = (ImageButton) findViewById(R.id.ib_lvl_7);
		levels[7] = (ImageButton) findViewById(R.id.ib_lvl_8);
		levels[8] = (ImageButton) findViewById(R.id.ib_lvl_9);
		levels[9] = (ImageButton) findViewById(R.id.ib_lvl_10);
		levels[10] = (ImageButton) findViewById(R.id.ib_lvl_11);
		levels[11] = (ImageButton) findViewById(R.id.ib_lvl_12);
		levels[12] = (ImageButton) findViewById(R.id.ib_lvl_13);
		levels[13] = (ImageButton) findViewById(R.id.ib_lvl_14);
		levels[14] = (ImageButton) findViewById(R.id.ib_lvl_15);
		levels[15] = (ImageButton) findViewById(R.id.ib_lvl_16);
		levels[16] = (ImageButton) findViewById(R.id.ib_lvl_17);
		levels[17] = (ImageButton) findViewById(R.id.ib_lvl_18);
		levels[18] = (ImageButton) findViewById(R.id.ib_lvl_19);
		levels[19] = (ImageButton) findViewById(R.id.ib_lvl_20);

		for (int i = 1; i < levels.length; i++) {
			levels[i].setEnabled(false);
		}

		levels[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setCongratulationView(true);

			}
		});

		Button bt_next_lvl = (Button) findViewById(R.id.bt_next_lvl);
		bt_next_lvl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setCongratulationView(false);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_easy_mode_game, menu);
		return true;
	}

	protected void setCongratulationView(boolean value) {
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl_congratulations);
		if (value)
			rl.setVisibility(View.VISIBLE);
		else
			rl.setVisibility(View.INVISIBLE);

	}
}
