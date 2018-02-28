package com.example.sharedcode.communication;

import com.example.sharedcode.model.UpdateType;

/**
 * Created by mboyd6 on 2/27/2018.
 */

public class UpdateArgs {
    public UpdateArgs(UpdateType type, boolean success, String error) {
        this.error = error;
        this.type = type;
        this.success = success;
    }

    public String error;
    public UpdateType type;
    /**
     * success is true by default
     */
    public boolean success = true;


}
