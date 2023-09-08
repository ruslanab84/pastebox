package com.sendel.pastebox.controller;

import com.sendel.pastebox.api.reguest.PasteBoxRequest;
import com.sendel.pastebox.api.reguest.PasteBoxResponse;
import com.sendel.pastebox.api.reguest.PasteBoxUrlResponse;
import com.sendel.pastebox.service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;


@RestController
@RequiredArgsConstructor
public class PasteBox {

    private final PasteBoxService pasteBoxService;
    @GetMapping("/")
    public Collection<PasteBoxResponse> getPublicPasteList(){
        return pasteBoxService.getFirstPublicPasteboxes();
    }

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash){
        return pasteBoxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteBoxRequest request){
        return pasteBoxService.create(request);
    }
}
