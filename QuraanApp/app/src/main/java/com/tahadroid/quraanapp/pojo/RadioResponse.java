package com.tahadroid.quraanapp.pojo;

import java.util.List;

public class RadioResponse {
    List<RadioItem> radios;

    public RadioResponse(List<RadioItem> radios) {
        this.radios = radios;
    }

    public List<RadioItem> getRadios() {
        return radios;
    }

    public void setRadios(List<RadioItem> radios) {
        this.radios = radios;
    }
}
