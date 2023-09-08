package com.sendel.pastebox.api.reguest;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasteBoxUrlResponse {
    private final String url;
}
