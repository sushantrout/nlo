package com.nlo.validation;


import com.nlo.model.TDPLiveDTO;
import jakarta.xml.bind.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class TDPLiveValidation implements Validation<TDPLiveDTO> {
    @Override
    public void validate(TDPLiveDTO tdpLiveDTO) throws ValidationException {

    }
}
