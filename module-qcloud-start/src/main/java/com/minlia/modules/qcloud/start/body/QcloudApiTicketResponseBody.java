package com.minlia.modules.qcloud.start.body;

import lombok.Data;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class QcloudApiTicketResponseBody extends QcloudResponseBaseBody {

    private List<QcloudApiTickets> tickets;

}