package kz.med.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PaginationObject {

    private Object list;

    private Integer totalSize;

    private Integer size;

    private Integer page;

}
