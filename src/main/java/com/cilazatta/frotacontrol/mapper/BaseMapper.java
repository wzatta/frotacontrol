package com.cilazatta.frotacontrol.mapper;

public interface BaseMapper<E, REQ, RES, UPD> {

    E toEntity(REQ request);

    RES toResponse(E entity);

    void updateEntity(E entity, UPD requestUpd);
}