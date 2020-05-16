package io.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionalMapperImplTest {

    @Data
    private class Employee {
        private String name;
        private int age;
        private Date hireDate;
        private Department department;
    }

    @Data
    @AllArgsConstructor
    private class Department {

        private long id;
        private String name;

    }

    @Data
    private class EmployeeDTO {
        private String name;
        private String age;
        private String hireDate;
        private String department;

    }


    @Test
    public void entityToDTO() throws ParseException {

        Employee entity = init();
        FunctionalMapper mapper = new FunctionalMapperImpl();

        EmployeeDTO dto = new EmployeeDTO();
        mapper.map(dto::setName,
                entity::getName);

        mapper.map(dto::setAge,
                entity::getAge,
                integer -> integer.toString());

        mapper.map(dto::setDepartment,
                entity::getDepartment,
                Department::getName);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        mapper.map(dto::setHireDate,
                entity::getHireDate,
                sdf::format);


        assert dto.getName().equals(entity.getName());
        assert dto.getDepartment().equals(entity.getDepartment().getName());
        assert sdf.parse(dto.getHireDate()).equals(entity.getHireDate());
        assert dto.getAge().equals(entity.getAge());
    }


    private Employee init() {

        Employee e = new Employee();
        e.setName("Eugenio");
        e.setAge(32);
        e.setDepartment(new Department(1, "Developer"));
        e.setHireDate(new Date());
        return e;
    }
}
