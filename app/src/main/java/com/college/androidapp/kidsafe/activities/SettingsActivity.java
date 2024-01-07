package com.college.androidapp.kidsafe.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.college.androidapp.kidsafe.R;
import com.college.androidapp.kidsafe.dialogfragments.AccountDeleteDialogFragment;
import com.college.androidapp.kidsafe.dialogfragments.ConfirmationDialogFragment;
import com.college.androidapp.kidsafe.dialogfragments.LanguageSelectionDialogFragment;
import com.college.androidapp.kidsafe.dialogfragments.PasswordChangingDialogFragment;
import com.college.androidapp.kidsafe.interfaces.OnConfirmationListener;
import com.college.androidapp.kidsafe.interfaces.OnDeleteAccountListener;
import com.college.androidapp.kidsafe.interfaces.OnLanguageSelectionListener;
import com.college.androidapp.kidsafe.interfaces.OnPasswordChangeListener;
import com.college.androidapp.kidsafe.utils.AccountUtils;
import com.college.androidapp.kidsafe.utils.Constant;
import com.college.androidapp.kidsafe.utils.LocaleUtils;
import com.college.androidapp.kidsafe.utils.SharedPrefsUtils;

public class SettingsActivity extends AppCompatActivity implements OnLanguageSelectionListener, OnConfirmationListener, OnPasswordChangeListener, OnDeleteAccountListener {
	private Button btnLanguageSelection;
	private Button btnLogout;
	private Button btnChangePassword;
	private Button btnDeleteAccount;
	private Button btnAbout;
	private ImageButton btnBack;
	private ImageButton btnSettings;
	private TextView txtTitle;
	private FrameLayout toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		toolbar = findViewById(R.id.toolbar);
		btnBack = findViewById(R.id.btnBack);
		btnBack.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_back));
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		btnSettings = findViewById(R.id.btnSettings);
		btnSettings.setVisibility(View.GONE);
		txtTitle = findViewById(R.id.txtTitle);
		txtTitle.setText(getString(R.string.settings));
		
		btnLanguageSelection = findViewById(R.id.btnLanguageSelection);
		btnLanguageSelection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				selectLanguage();
			}
		});
		
		btnLogout = findViewById(R.id.btnLogout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				logout();
			}
		});
		
		
		btnChangePassword = findViewById(R.id.btnChangePassword);
		btnChangePassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changePassword();
			}
		});
		
		
		btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
		btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				deleteAccount();
			}
		});
		
		
		btnAbout = findViewById(R.id.btnAbout);
		btnAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showAbout();
			}
		});


	}
	
	private void showAbout() {
		startActivity(new Intent(this, AboutActivity.class));
	}
	

	private void deleteAccount() {
		AccountDeleteDialogFragment accountDeleteDialogFragment = new AccountDeleteDialogFragment();
		accountDeleteDialogFragment.setCancelable(false);
		accountDeleteDialogFragment.show(getSupportFragmentManager(), Constant.ACCOUNT_DELETE_DIALOG_FRAGMENT_TAG);
	}
	
	private void changePassword() {
		PasswordChangingDialogFragment passwordChangingDialogFragment = new PasswordChangingDialogFragment();
		passwordChangingDialogFragment.setCancelable(false);
		passwordChangingDialogFragment.show(getSupportFragmentManager(), Constant.PASSWORD_CHANGING_DIALOG_FRAGMENT_TAG);
	}
	
	private void logout() {
		ConfirmationDialogFragment confirmationDialogFragment = new ConfirmationDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constant.CONFIRMATION_MESSAGE, getString(R.string.do_you_want_to_logout));
		confirmationDialogFragment.setArguments(bundle);
		confirmationDialogFragment.setCancelable(false);
		confirmationDialogFragment.show(getSupportFragmentManager(), Constant.CONFIRMATION_FRAGMENT_TAG);
	}
	
	private void selectLanguage() {
		LanguageSelectionDialogFragment languageSelectionDialogFragment = new LanguageSelectionDialogFragment();
		languageSelectionDialogFragment.setCancelable(false);
		languageSelectionDialogFragment.show(getSupportFragmentManager(), Constant.LANGUAGE_SELECTION_DIALOG_FRAGMENT_TAG);
	}
	
	@Override
	public void onLanguageSelection(String language) {
		String appLanguage = SharedPrefsUtils.getStringPreference(this, Constant.APP_LANGUAGE, "en");
		if (language.equals("English") && !appLanguage.equals("en")) {
			LocaleUtils.setLocale(this, "en");
		} else if (language.equals("Arabic") && !appLanguage.equals("ar")) {
			LocaleUtils.setLocale(this, "ar");
			
		}
		
		restartApp();
		
	}
	
	private void restartApp() {
		Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
	}
	
	@Override
	public void onConfirm() {
		AccountUtils.logout(this);
	}
	
	@Override
	public void onConfirmationCancel() {
		//DO NOTHING
	}
	
	@Override
	public void onPasswordChange(String newPassword) {
		AccountUtils.changePassword(this, newPassword);
		
	}
	
	@Override
	public void onDeleteAccount(String password) {
		AccountUtils.deleteAccount(this, password);
		
	}
}
