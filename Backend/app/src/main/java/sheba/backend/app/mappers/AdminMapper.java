package sheba.backend.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sheba.backend.app.DTO.AdminDTO;
import sheba.backend.app.entities.Admin;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminDTO adminToAdminDTO(Admin admin);
    Admin adminDTOToAdmin(AdminDTO adminDTO);
}
