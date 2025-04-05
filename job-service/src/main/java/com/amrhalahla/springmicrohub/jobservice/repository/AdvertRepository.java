package com.amrhalahla.springmicrohub.jobservice.repository;

import com.amrhalahla.springmicrohub.jobservice.enums.Advertiser;
import com.amrhalahla.springmicrohub.jobservice.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, String> {
    List<Advert> getAdvertsByUserIdAndAdvertiser(String id, Advertiser advertiser);
}
