package com.college.androidapp.kidsafe.interfaces;

import com.college.androidapp.kidsafe.models.User;

public interface OnChildClickListener {
    void onItemClick(/*View view, */int position);

    void onWebFilterClick(boolean checked, User child);

    void onBtnLockClick(boolean checked, User child);

    void onLockPhoneSet(int hours, int minutes);

    void onLockCanceled();

    //void onLockDismiss();


}
