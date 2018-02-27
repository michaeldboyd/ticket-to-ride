package com.example.sharedcode.interfaces;

import com.example.sharedcode.model.UpdateType;

public interface IUtility {
    public void handleError(UpdateType type, String message);

    public void handleLoginError(UpdateType type, String message);
}
