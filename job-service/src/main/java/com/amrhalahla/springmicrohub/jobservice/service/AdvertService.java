package com.amrhalahla.springmicrohub.jobservice.service;

import com.amrhalahla.springmicrohub.jobservice.client.FileStorageClient;
import com.amrhalahla.springmicrohub.jobservice.client.UserServiceClient;
import com.amrhalahla.springmicrohub.jobservice.dto.UserDto;
import com.amrhalahla.springmicrohub.jobservice.enums.AdvertStatus;
import com.amrhalahla.springmicrohub.jobservice.enums.Advertiser;
import com.amrhalahla.springmicrohub.jobservice.exc.NotFoundException;
import com.amrhalahla.springmicrohub.jobservice.model.Advert;
import com.amrhalahla.springmicrohub.jobservice.model.Job;
import com.amrhalahla.springmicrohub.jobservice.repository.AdvertRepository;
import com.amrhalahla.springmicrohub.jobservice.request.advert.AdvertCreateRequest;
import com.amrhalahla.springmicrohub.jobservice.request.advert.AdvertUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final JobService jobService;
    private final UserServiceClient userServiceclient;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;

    public Advert createAdvert(AdvertCreateRequest request, MultipartFile file) {
        String userId = getUserById(request.getUserId()).getId();
        Job job = jobService.getJobById(request.getJobId());

        String imageId = null;

        if (file != null)
            imageId = fileStorageClient.uploadImageToFIleSystem(file).getBody();

        Advert toSave = Advert.builder()
                .userId(userId)
                .job(job)
                .name(request.getName())
                .advertiser(request.getAdvertiser())
                .deliveryTime(request.getDeliveryTime())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(AdvertStatus.OPEN)
                .imageId(imageId)
                .build();
        return advertRepository.save(toSave);
    }

    public List<Advert> getAll() {
        return advertRepository.findAll();
    }

    public Advert getAdvertById(String id) {
        return findAdvertById(id);
    }

    public List<Advert> getAdvertsByUserId(String id, Advertiser type) {
        String userId = getUserById(id).getId();
        return advertRepository.getAdvertsByUserIdAndAdvertiser(userId, type);
    }

    public UserDto getUserById(String id) {
        return Optional.ofNullable(userServiceclient.getUserById(id).getBody())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Advert updateAdvertById(AdvertUpdateRequest request, MultipartFile file) {
        Advert toUpdate = findAdvertById(request.getId());
        modelMapper.map(request, toUpdate);

        if (file != null) {
            String imageId = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (imageId != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getImageId());
                toUpdate.setImageId(imageId);
            }
        }

        return advertRepository.save(toUpdate);
    }

    public void deleteAdvertById(String id) {
        advertRepository.deleteById(id);
    }

    public boolean authorizeCheck(String id, String principal) {
        return getUserById(getAdvertById(id).getUserId()).getUsername().equals(principal);
    }

    protected Advert findAdvertById(String id) {
        return advertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Advert not found"));
    }
}
