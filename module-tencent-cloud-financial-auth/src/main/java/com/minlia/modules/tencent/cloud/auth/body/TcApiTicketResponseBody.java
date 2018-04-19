package com.minlia.modules.tencent.cloud.auth.body;

import lombok.Data;

import java.util.List;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class TcApiTicketResponseBody extends TcResponseBaseBody{

    private List<TcApiTickets> tickets;

}