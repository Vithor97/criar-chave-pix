package com.vitor.criar_chave_pix.application.validators;

import com.vitor.criar_chave_pix.application.validators.chain.*;

public class ChavePixValidatorChain {

    private final AbstractValidator chaveValidatorChain;

    public ChavePixValidatorChain(){
        this.chaveValidatorChain = buildValidacoesChaves();
    }

    private AbstractValidator buildValidacoesChaves(){
        AbstractValidator EVPValidator = new EVPValidator(null);
        AbstractValidator numeroValidator = new NumeroValidator(EVPValidator);
        AbstractValidator emailValidator = new EmailValidator(numeroValidator);
        AbstractValidator cnpjValidator = new CnpjValidator(emailValidator);
        AbstractValidator cpfValidator = new CpfValidator(cnpjValidator);


        return new TipoChaveValidator(cpfValidator);
    }

    public void validate(String tipoChave, String valorChave){
        this.chaveValidatorChain.validate(tipoChave, valorChave);
    }
}
