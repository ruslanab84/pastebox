package com.sendel.pastebox.service;

import com.sendel.pastebox.api.reguest.PasteBoxRequest;
import com.sendel.pastebox.api.reguest.PasteBoxResponse;
import com.sendel.pastebox.api.reguest.PasteBoxUrlResponse;

import java.util.List;

public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteboxes();
    PasteBoxUrlResponse create(PasteBoxRequest request);
}
