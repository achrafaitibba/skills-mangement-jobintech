package com.achrafaitibba.itskillsmanagement.controller;

import com.achrafaitibba.itskillsmanagement.dto.OfferResponse;
import com.achrafaitibba.itskillsmanagement.model.Offer;
import com.achrafaitibba.itskillsmanagement.repository.OfferRepository;
import com.achrafaitibba.itskillsmanagement.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final OfferRepository offerRepository;


    @PostMapping("/create")
    public OfferResponse createOffer(@RequestBody Offer offer){
        return offerService.createOffer(offer);
    }

    @GetMapping()
    public List<OfferResponse> getAllOffers(){
        return offerService.getAllOffers();
    }

    @GetMapping("/{offerId}")
    public Offer getOfferById(@PathVariable Long offerId){
        return offerRepository.findById(offerId).get();
    }

    @PostMapping("/apply/{offerId}")
    public ResponseEntity<Void> applyForOffer(@PathVariable Long offerId){
                offerService.applyForOffer(offerId);
        return null;
    }
}
