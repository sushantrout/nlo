package com.nlo.validation;

import jakarta.xml.bind.ValidationException;

public interface Validation<D> {
    void validate(D d) throws ValidationException;
}