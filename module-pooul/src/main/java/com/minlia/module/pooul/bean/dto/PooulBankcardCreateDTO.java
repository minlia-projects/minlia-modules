package com.minlia.module.pooul.bean.dto;

import lombok.Data;

/**
 * Created by garen on 2018/9/21.
 */
@Data
public class PooulBankcardCreateDTO extends PooulDTO {

    private Data data;

    public class Data {

        private Long _id;

        public Long get_id() {
            return _id;
        }

        public void set_id(Long _id) {
            this._id = _id;
        }
    }

}
