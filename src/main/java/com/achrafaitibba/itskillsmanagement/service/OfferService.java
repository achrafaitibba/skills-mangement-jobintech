package com.achrafaitibba.itskillsmanagement.service;

import com.achrafaitibba.itskillsmanagement.configuration.token.JwtService;
import com.achrafaitibba.itskillsmanagement.dto.OfferResponse;
import com.achrafaitibba.itskillsmanagement.enums.Role;
import com.achrafaitibba.itskillsmanagement.enums.Status;
import com.achrafaitibba.itskillsmanagement.exception.CustomErrorMessage;
import com.achrafaitibba.itskillsmanagement.exception.RequestException;
import com.achrafaitibba.itskillsmanagement.model.Application;
import com.achrafaitibba.itskillsmanagement.model.Offer;
import com.achrafaitibba.itskillsmanagement.repository.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final HttpServletRequest httpServletRequest;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RecruiterRepository recruiterRepository;
    private final ApplicationRepository applicationRepository;
    private final CandidateRepository candidateRepository;

    public OfferResponse createOffer(Offer offer) {
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();
        String role = userRepository.findByEmail(email).get().getRole().toString();
        Offer offerToSave = Offer.builder()
                .title(offer.getTitle())
                .description(offer.getDescription())
                .salary(offer.getSalary())
                .location(offer.getLocation())
                .recruiter(recruiterRepository.findByUserEmail(email).get())
                .build();

        if (role.equals(Role.RECRUITER.toString())) {
            if (offerToSave.getRecruiter().getCompanyName() == null) {
                throw new RequestException(CustomErrorMessage.NO_COMPANY_NAME.getMessage(), HttpStatus.UNAUTHORIZED);
            } else offerToSave = offerRepository.save(offerToSave);

        } else {
            throw new RequestException(CustomErrorMessage.NOT_AUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new OfferResponse(
                offerToSave.getId(),
                offerToSave.getTitle(),
                offerToSave.getDescription(),
                offerToSave.getSalary(),
                offerToSave.getLocation(),
                offerToSave.getRecruiter().getCompanyName());

    }


    //todo
    public List<Offer> getAllOffersByRecruiter() {
        return null;

    }


    //todo
    public void applyForOffer(Long offerId) {
        String header = httpServletRequest.getHeader("Authorization");
        String jwt = header.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
        String email = claims.getSubject();
        String role = userRepository.findByEmail(email).get().getRole().toString();
        if (role.equals(Role.CANDIDATE.toString())) {
            Offer offer = offerRepository.findById(offerId).get();
            applicationRepository.save(
                    Application.builder()
                            .date(new Date())
                            .status(Status.PENDING)
                            .feedback("")
                            .candidate(candidateRepository.findCandidateByUserEmail(email).get())
                            .offer(offer)
                            .build()
            );
        } else {
            throw new RequestException(CustomErrorMessage.NOT_AUTHORIZED.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    public List<OfferResponse> getAllOffers() {
        List<Offer> allOffers = offerRepository.findAll();
        return allOffers.stream().map(
                offer ->
                        new OfferResponse(
                                offer.getId(),
                                offer.getTitle(),
                                offer.getDescription(),
                                offer.getSalary(),
                                offer.getLocation(),
                                offer.getRecruiter().getCompanyName()
                        )

        ).collect(Collectors.toList());
    }
}

