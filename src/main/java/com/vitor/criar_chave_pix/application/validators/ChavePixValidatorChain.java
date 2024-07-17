package com.vitor.criar_chave_pix.application.validators;

import com.vitor.criar_chave_pix.application.validators.chain.*;

public class ChavePixValidatorChain {

    private final Validator chaveValidatorChain;

    public ChavePixValidatorChain(){
        this.chaveValidatorChain = buildValidacoesChaves();
    }

    private Validator buildValidacoesChaves(){
        Validator tipoChaveValidator = new TipoChaveValidator();
        Validator emailValidator = new EmailValidator();
        Validator cnpjValidator = new CnpjValidator();
        Validator cpfValidator = new CpfValidator();
        Validator numeroValidator = new NumeroValidator();
        Validator EVPValidator = new EVPValidator();

        tipoChaveValidator.setNext(cpfValidator);

        cpfValidator.setNext(cnpjValidator);
        cnpjValidator.setNext(emailValidator);
        emailValidator.setNext(numeroValidator);
        numeroValidator.setNext(EVPValidator);

        return tipoChaveValidator;
    }

    public void validate(String tipoChave, String valorChave){
        this.chaveValidatorChain.validate(tipoChave, valorChave);
    }
}
