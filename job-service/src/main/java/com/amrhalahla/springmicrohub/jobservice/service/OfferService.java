package com.amrhalahla.springmicrohub.jobservice.service;

import com.amrhalahla.springmicrohub.jobservice.client.UserServiceClient;
import com.amrhalahla.springmicrohub.jobservice.dto.UserDto;
import com.amrhalahla.springmicrohub.jobservice.enums.OfferStatus;
import com.amrhalahla.springmicrohub.jobservice.exc.NotFoundException;
import com.amrhalahla.springmicrohub.jobservice.model.Advert;
import com.amrhalahla.springmicrohub.jobservice.model.Offer;
import com.amrhalahla.springmicrohub.jobservice.repository.OfferRepository;
import com.amrhalahla.springmicrohub.jobservice.request.notification.SendNotificationRequest;
import com.amrhalahla.springmicrohub.jobservice.request.offer.MakeAnOfferRequest;
import com.amrhalahla.springmicrohub.jobservice.request.offer.OfferUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final AdvertService advertService;
    private final UserServiceClient userServiceclient;
    private final KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;
    private final NewTopic topic;
    private final ModelMapper modelMapper;

    public Offer makeAnOffer(MakeAnOfferRequest request) {
        String userId = getUserById(request.getUserId()).getId();
        Advert advert = advertService.getAdvertById(request.getAdvertId());
        Offer toSave = Offer.builder()
                .userId(userId)
                .advert(advert)
                .offeredPrice(request.getOfferedPrice())
                .status(OfferStatus.OPEN).build();
        offerRepository.save(toSave);

        SendNotificationRequest notification = SendNotificationRequest.builder()
                .message("You have received an offer for your advertising.")
                .userId(advert.getUserId())
                .offerId(toSave.getId()).build();

        kafkaTemplate.send(topic.name(), notification);
        return toSave;
    }

    public Offer getOfferById(String id) {
        return findOfferById(id);
    }

    public List<Offer> getOffersByAdvertId(String id) {
        Advert advert = advertService.getAdvertById(id);
        return offerRepository.getOffersByAdvertId(advert.getId());
    }

    public List<Offer> getOffersByUserId(String id) {
        String userId = getUserById(id).getId();
        return offerRepository.getOffersByUserId(userId);
    }

    public UserDto getUserById(String id) {
        return Optional.ofNullable(userServiceclient.getUserById(id).getBody())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Offer updateOfferById(OfferUpdateRequest request) {
        Offer toUpdate = findOfferById(request.getId());
        modelMapper.map(request, toUpdate);
        return offerRepository.save(toUpdate);
    }

    public void deleteOfferById(String id) {
        offerRepository.deleteById(id);
    }

    public boolean authorizeCheck(String id, String principal) {
        return getUserById(getOfferById(id).getUserId()).getUsername().equals(principal);
    }

    protected Offer findOfferById(String id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Offer not found"));
    }
}
