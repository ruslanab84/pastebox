package com.sendel.pastebox.service;

import com.sendel.pastebox.api.reguest.PasteBoxRequest;
import com.sendel.pastebox.api.reguest.PasteBoxResponse;
import com.sendel.pastebox.api.reguest.PasteBoxUrlResponse;
import com.sendel.pastebox.api.reguest.PublicStatus;
import com.sendel.pastebox.repository.PasteBoxEntity;
import com.sendel.pastebox.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class PasteBoxServiceImpl implements PasteBoxService {

    private final PasteBoxRepository pasteBoxRepository;
    public String host = "http://abc.ru";
    public int publicListSize = 10;
    private AtomicInteger atomicLong = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = pasteBoxRepository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(),pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteboxes() {
        List<PasteBoxEntity> list=pasteBoxRepository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(
                pasteBoxEntity ->
                    new PasteBoxResponse(pasteBoxEntity.getData(),pasteBoxEntity.isPublic())).
                collect(Collectors.toList());
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus()== PublicStatus.PUBLIC);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        pasteBoxRepository.add(pasteBoxEntity);
        return new PasteBoxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId(){
        return atomicLong.getAndIncrement();
    }
}
