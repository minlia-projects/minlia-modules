package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.ro.AttachmentBase64UploadRO;
import com.minlia.modules.attachment.service.AttachmentUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by will on 6/28/17.
 */
@Api(tags = "System Attachment", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/attachment/upload")
public class AttachmentUploadEndpoint {

    @Autowired
    private AttachmentUploadService attachmentUploadService;

    @ApiOperation(value = "上传", notes = "上传", httpMethod = "POST")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response upload(MultipartFile file) throws Exception {
        return attachmentUploadService.upload(file);
    }

    @ApiOperation(value = "上传", notes = "上传", httpMethod = "POST")
    @PostMapping(value = "base64", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response upload(@RequestBody AttachmentBase64UploadRO uploadBody) throws Exception {
//        //将字符串转换为byte数组
//        byte[] bytes = new BASE64Decoder().decodeBuffer(uploadBody.getFile().trim());
//        //转化为输入流
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
//        uploadBody.setInputStream(inputStream);
//        uploadBody.setSize(Long.valueOf(bytes.length));
        return attachmentUploadService.uploadByBase64(uploadBody, null, null);
    }

    @ApiOperation(value = "上传", notes = "上传", httpMethod = "POST")
    @PostMapping(value = "/{relationId}/{belongsTo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response upload(MultipartFile file, @PathVariable String relationId, @PathVariable String belongsTo) throws Exception {
        //检查是否满足上传条件 TODO 上传数量、belongsTo是否存在
//        String key = keyGenerate(file,relationId,belongsTo);
//        AttachmentDTO ossFile=null;
//        try {
//            ossFile = ossService.upload(file, key);
//        } catch (Exception e) {
//            ApiPreconditions.checkNotNull(e, UploadCode.E22, e.getMessage());
//        }
//        Attachment attachment = Attachment.builder().belongsTo(belongsTo).relationId(relationId).name(ossFile.getOriginalName()).type(ossFile.getContentType()).url(ossFile.getUrl()).size(ossFile.getSize()).accessKey(ossFile.geteTag()).build();

        //附件记录
        return attachmentUploadService.upload(file, relationId, belongsTo);
    }

    public static void main(String[] agrs) throws IOException {
        byte[] data = null;

        // 读取图片字节数组
        try {

            InputStream in = new FileInputStream("/Users/garen/Pictures/下载.jpeg");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        System.out.println(encoder.encode(data)); // 返回Base64编码过的字节数组字符串

        byte[] bytes = new BASE64Decoder().decodeBuffer(encoder.encode(data).trim());
        //转化为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        FileUtils.copyInputStreamToFile(inputStream, new File("static/下载1.jpeg"));

        String absolutePath = new File("static/下载1.jpeg").getAbsolutePath();
        System.out.println(absolutePath);

        {
            "file": "/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxITEhUSExMWFhUXGB8YGBgYFhcfHhcdGBsfGxod\n" +
                "GBsYHiggGx8lIh8dIjEhJSkrLi4uGiAzODMsNygtLisBCgoKDg0OGxAQGy0lICYtLS8rLS0tLS0v\n" +
                "LS0tLS0tLS0tLS0tLS0vLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIALwAvAMBEQACEQED\n" +
                "EQH/xAAcAAACAwADAQAAAAAAAAAAAAAABgQFBwECAwj/xABGEAACAQMBBgIGBgcFBwUAAAABAgMA\n" +
                "BBEFBhIhMUFRE2EHIjJxgZEUI0JSobEkM0NictHwFVOCwfFEc5KTorLhFlRjg8L/xAAaAQACAwEB\n" +
                "AAAAAAAAAAAAAAAABAEDBQIG/8QAMxEAAgIBBAECAwYGAwEBAAAAAAECAxEEEiExQRNRImFxBTKB\n" +
                "ocHRFCNCkbHwUmLx4TP/2gAMAwEAAhEDEQA/ANxoAKACgAoAKAIesapFbQvPMwSONd5j7ugHUnkB\n" +
                "QBi99q91qs8E0iFbMXBj+ikHgFQssk3LJzukDlWXrdalGVcXh7cp/j0h3T0NtSa89DJo2lCAz7p4\n" +
                "SymQAD2coq4+amvP3XOxRz4WPzZqV17M/N5LKqS0KAPBpo23kLKceq67w4ZHIjpwNdbZLDx9DnMX\n" +
                "wcxxRhVQBd1cbo4YG7yx7qG5NtvsMRSwe1cnQUAFBAUEhQAUAV2taJBdJ4c8YYdD9pfNTzFW032U\n" +
                "y3QZXZVGxYkihtta1HRjlma9sRw4/rIh7/Lvy91eh0n2hG546l7eH9DKu0rr57Xv7GsbN7Q299CJ\n" +
                "7eQOp5jqp7MOhrTTyJtYLWpICgAoAKACgAoAKACgDJ9qrs6lqP0bP6JZEGQdJZ+YB7he3fNZn2nq\n" +
                "vRr2x7f+BzR0epLL6RcKgGcDGTk+Zxj8gK8xk2cHagkp9Y1ZQjpGx3yCAw5Kehz1x2q+qptpy6Kb\n" +
                "LFjC7FLTdoLmUsshK8eIHDdYe0O+DwYe/wAq0LdNVBJx/wBX+8MVhdOXDEXWbqW2vpHU8WO9x5Mp\n" +
                "HX+ula1FcLtOosRslKu1sZtG2lhnwpIjk+6x5/wnrWffo518rlDNd0Z/Un6vrDWqCUBiu9ht1sYB\n" +
                "5Hz41VTQrpbcnc7HWski12pM8LiKXeJQgqfaGR251xLSelNb15Olfvi8M9dD2hdprdpAd02mGx97\n" +
                "eTiQfjUX6eKhNR/5fudV3PdFv2G+01ASSFVwVCg5zxySeGPIfnWfKvbHL7Goz3PgnVWWBQAUAcEU\n" +
                "ECDeaNd6fdG803JJ3nlhxiMrkYQAcSeZ/EY5Vv6L7RW1Rtfsk/3MvUaR5zA1nYjbK31KHxIjuuuP\n" +
                "EiJ9aMnv3HPB61tmcMlABQAUAFABQAUAQ9YuWigmlRS7JGzKijJYqpIAHUk8KAMx2K04w2qb/wCt\n" +
                "lJmlJ5l5OJz+A+FeR11/q3yfjpfgbumr2VpHjtdtfBYjD+vKRlY1PMdCx+yPOp0ugs1D44XuF2ph\n" +
                "Uue/Yy3XPSFe3GQGEKfdjHH4seJ/Ct2j7Mor5fL+ZmWay2fXAsTXTv7Ujt72JrQjXBdIVc5Ptmgz\n" +
                "+iyVNLGprcqW8MTNHjACEZ4PniwHTHlQ0n2GWZ9PcO+N5y27y3jkj40RrjH7qwDm32eeKsOSRHey\n" +
                "qpQO24RgqTkY9x5VVKmDe7HJ2rJJYyeUErIwZWKsORFdygpLEjlSaeUOmg7QCZTE4Cy7hVCOAbhy\n" +
                "HY+VY+o0jqe5cxzyPVXKaw+yJsxtQ6MIp2OOSuTxUjox6jzqzVaNOO+v+xzTe09sjTbHXmXAf1h3\n" +
                "6/8AmsKdCfRoxua7GC2uUkGUOfzHvFKyi4vDGIyT6PaoOgoAKAEzaPRZracanp3qzpkyxgcJV5ng\n" +
                "OfmOvPmK2fs77Q2Yqs68P2M7V6XPxw78mo7E7WQ6lbLPEcHlJHnjG3UHy7HrXoTKGCgAoAKACgAo\n" +
                "AVfSXqsttYmSFt2TxYlU+bSKMHyPI1zOW2LZMVlpHnueOniAKsoH1katkZ7qcDPyrzl9UNVF3U/e\n" +
                "Xa9/mjWrnKlqFnXhmc7ZbQ20b+E4TfPJiBkNyBc8wg5fPtXWg007Y85S/wAEaq6MH8zMNe1G68Ro\n" +
                "5cJj7KgAY6EHqCOtbtWlrgZs7pyIeoaLcQpFLNC8aTDejZhjfA6j/wA0ysFR5nUZ/C8DxpPBznw9\n" +
                "9tzOc+znHPjRgjJFqQCgAoAKAOVOCCOBHEHtUNJrDBPAOckk8ycn40JYWAbGrR9o2Fs8ZOJY0zGT\n" +
                "x3gOnvArLv0i9VS8N8jld72NeUMGye0hm5HcmUcQORHceXlSWr0npv3TGKL931H3TdaD4V8K3fof\n" +
                "5VlWUtcoehbnhlvVBcFABQAkamkmk3Y1O1UmFzi6hXlgn2gOXn5HyJr0H2Zrty9Kb58fsZOs0234\n" +
                "49eTa9Mv454kmiYNG6hlYdQa2jPJVABQAUAFACN6ZJQunh2BKrcQsQBkkCQE8Krti5VyS9mdQeJJ\n" +
                "v3MhvPSvLG36HAkODzfiT3yowB+NZ2l+zHXJWSnl/Ibu1e5OKjwcbTadDqsL6nZLidAPplqOJGOc\n" +
                "sY5le/u75rWXw8CT5FjTbqO4jW1uGCsvCCY/Yz+zkP8Adk8j9k+VSQRNburolYLl5CYBuKjnIQfu\n" +
                "+R4ceoxUohlZUgGaAAmgBg0XZSaYeI/1UeM5I4kDsO3maWs1MY8LljVWllNZfCKBsZOOIzwNMJ8C\n" +
                "r7OBUgFAARUNZAk6XeGGVJR9k8R3HUVVdUrIOJ3XPbLJp2m6jHOm/G2R1HUHsRXnrapVyxJGpGak\n" +
                "sovtI2iVZBbucnGc/cH7x6DtStumbjvX/pfXdh7WNVJDQUAQdZvYYYWecgR43SCM72fsgdSe1WU1\n" +
                "znNKvsrslGMcy6EOyutZsF8GwR/ozEyxq67zIH+y3Y8M48/OvS0/aFTjicllcMx7NLPPwrg+iK0R\n" +
                "UKACgAoAUvSpETpk7jnFuzD/AOpw3+Vczjui4+6Ji8NMzf0gPZpai4aGJpHwY8qMsSM/IczXmtAr\n" +
                "p2+mpPC7NfVOuMNzSyZTo2uz2twLmF92QHPL1WHVWXqp5Yr1EYJLBjOTbyX+02nwXcTajZLuj/ar\n" +
                "cc4GP21HWJu/Q5+ErgCnhvFuEWGdgHQYhmPQdI5T1Tsfs+6joCrubSRMhlK4O6c9/Lv3zUOXOETt\n" +
                "4ycWts8jCONSzHkB/X41MpKKywjFyeEaxsT6Nsbs0/FunDgP4QeZ8zWZdqXLhdGlTp4w5fLNEm0y\n" +
                "KOGTdXjuNxPE+yaWT5RfJ5TPl0HhW74MXycryoXRDOakAoA7bhxnpy91AErStSe3kEif4l6MOx/n\n" +
                "VF9EbY4ZZXY4PKHvToFkVXhciNyXkbPru33SegH8qxbJODamuVwvZL3H4pNZi+PI9bPah+yb/Cf8\n" +
                "qy76/wCpDtU/DL6lhg85YVbG8oODkZAOD3GetSpNdENJ9lPctqTOxjFtGgOFEgd2YD7RKsAM9qZi\n" +
                "tMkt25v5YRS/Wb4wka1XsDACgAoAKAI+oWaTRSQuMpIjIw7hgQfwNAHyrrFldXVybU8RZoYmfPqq\n" +
                "sZO9Ix7tjl5AdKT/AJWkTl5k/wDPgY+O9peyFWRRk7uSByJ7dz2p2LwsvsXffBY7O31xFOjW2TI3\n" +
                "q7mMiQHmrr1UjnUyaSywim3hFpd6NBLMJInEVuz7sob/AGZuOVP7rEYVuXHBquU2o5R1GCcsM99Z\n" +
                "h+kXK2MS58IBImHHoC2+eo8+mKrrlthvkXWx3T2Q6RqmxOwcVmgaT15TxY/5e7ypC66U2O1VxrXA\n" +
                "51QWnWRMgjuCPnQQz5T1GDckkj+67L8mxW5F5jkxpLEjwSu10cs5NDeAJ+t6Y1vKYjn2VYE9d5QT\n" +
                "+OR8Krps3xyW3VOuW1ka2m3WyRvA8GXuOoq0qJGpaf4e66neikGY3791bsy8iKhMCz2M1XwpfCY+\n" +
                "pJyz0bp8+XyrP19G6O9dr/A1prMPazQ43KkEcCDkVhtZWGPp4Y5WF2JUDD4jsaQnDa8DsZblkk1y\n" +
                "dBQA/V7o8yFACanpBhN0IDDKI2l8FZzu7jSZ3cYzvAFvVDEYJrhWRctueSx1TUd+OCr2x2gvWuZb\n" +
                "ezlEQgVCT4YbxJHBbdbe5KBjgBnjVV1/ptLBdp9N6sW84wSrb0hAafbTyxM1zOjEQxLkkpwdhk4C\n" +
                "g45nqBV0pqKy2LxhKTxFZMn2lKwWIntGeSO9VorhmGGW4DlnyvTeDFcdl86pt08bJwsf9J3CxwjK\n" +
                "PuLGraSbW0VXXEsrDez9kAZC1TTf69+V0uiyyv06+e2N3ob2d3xNeMuSA0UX8RX1iPmBnzNdayzl\n" +
                "ROtJD+od9itkUt7Jop41Z58tODxBLcl9y/nk0vZc3PMfBfClbcS8lR6PNDWxu57d4wJHUSwvnJ8P\n" +
                "OCme6nGffXd898VJf6zmiOyTizRaUGTigkKAPnf0q6UYNRmOPVlxKp/i9r5Nn8K1tNPdXgy9RHbM\n" +
                "XNSh3JWXGAcMPcwDD8DV1TyiqyOJHGnLmaIHrIo/6hU2PEWFazNGg+k7TQ0SXKYJjO62PutyPwP5\n" +
                "1naKzEtr8mp9oVJxU14M2rUMgutnNQi421zn6NKeJHOF+Syp5jkR1Ga5+ZJH2h0SW0mMUn8Ubr7M\n" +
                "iniroeoIo7QdGgaDf+NAkh9rGG944H+deb1Ffp2OJqVy3RTGXZ253ZN3o35jlSd8cxyM0yw8DRSY\n" +
                "0FAD9XujzIpJt7ASWKOIBKYmmIGBx3Q5HPwy2Rv9OZ4ca4VkXLanyduuSipNcMzu4tAdMvH4+JBN\n" +
                "MVOeRimLKR8MUnJbdQOxm3p8F7sjf+LbXN+wx48jzceiogQfD1CfjXGpe6zAadba8i/6N7b6Rbi5\n" +
                "kJVIYhBH2wmZJW492bGf3a71cstQQaP4czJ+zGxp1HT4izNHE93Pcjo2CpSEj4gN/rTkY4iosSnL\n" +
                "MnIQPSTcuwiinXduoXaOYdG4DckXyYcaS0ementmv6e1+xffarYRfnyaLsrdFIYLCwjWV0jVpZGy\n" +
                "EjL+sd7HFmPE7o6cyKlUu2TlLosdqqiorsYbyz1K3QyuYZ1Ay0aIUbz3GLEZ7BufcVZLSRxwVx1U\n" +
                "s8kURpdvZ30DeqhfJ5Eo6FWUjoQ4XI6FTSfMFKDG+JtSRf1UWnSOVWzgg4O6cdCOYNGCMnegkyj0\n" +
                "xTwXES7mC8TH6wkBWzwaNOsjZx7PAYOTTulUov6iWpakjONXgZoYLjdmK7giZ3QBMqMKqEe1gA5J\n" +
                "p2CcWxSbzgjaGiNcwLJjcMihs8sEjOfKurc7XgivG5ZN31P0e2DxSCKIxlkOPDkkCk44ZXe3Txx0\n" +
                "rKjfPKyaUqY7eD58ZSCQeYOD8K2E8oymsM6JURJY87L3kV9ANMu3COONnOf2bn9k5+43TsfhRjAH\n" +
                "js79ItZprOSPEqnO4x3eI4Ng4OcjBFZuvqi8TfA3pptZiNun3MmQzIUZTn2gc47EVk2Qj0nkci32\n" +
                "P8bhgGHIjPzrLaxwPp5O1BI/V7o8yZVLHHaXE9jMm+kil4M8nic+vGemUPD3EUhqIOuXqRNHTz9a\n" +
                "PpSFPT7e4t4LizEUsiSM+628gBWRQPWZyMkVzZOM5KecFlVcoQlW45OlvHfJZ/RRbEnwjHvLNHzY\n" +
                "EZwSO9ct1uzfu8+x1iyNWzZ490Tb55bfTRaIrq8kZhSMrhmLe37uZy3LjUR+K3dLoJKKp2x7f9zS\n" +
                "PR7rcckQs/Ae3kt40+rYqwKEEK6shIIJUjvkVpQnGazEy5wlB4kuRY2/0CLUr0qu6DaqqtIAMs75\n" +
                "bcY9Qq7p/wAdUX3bMItoq38kH0eXf9nXc0N2PDWZVljkPs5CYdS3IEbvKu6ZqUeDm+LUssc9R9Ie\n" +
                "m/R5JEuY3VQQcHvywOZz0xVxSL3o4ikFo0jqUEs0kqIeao54cOmeJ+NZepknZwaWmi1DkaaXGBen\n" +
                "0i5iuHntZE3ZSDLDKG3SwAXfRl4qSAMjBBxVqnFxxIqcJJ5iS9oLwQ2cssx4IhLBCRnsoPPicD41\n" +
                "zBZmkiZvEW2Vvo22QV1W9ulDzOobBHqxK3FY415KAMZxWvGKisIy5ScnljNrWw9vNZXFmqhVkZpE\n" +
                "4fq3J3uHlvZOOxIro5Pl1NOaK7W3nTDJMI5FP8WD8COtdeCDbdiL1ree502V/Vg+sgZj+xY8ASfu\n" +
                "5ArN1VWHuXkf01mVtZjW2dusd9dKjBl8VmUggjD+sMEds4p6l5rWRO1Ymzz1/TzC0KkYYwqWHYnJ\n" +
                "P51FMt2fqdXQ24+hWA1eUmnaDeDVkjVjjUrYeo2cfS4RwKk/3ijketL317oNFlcsSTJcFq6EgyOw\n" +
                "7OFyPiAD868/OSf9OGaaWPI66FLvQr5cPlWbcsTHanmJYVWWD9XujzIk+kuOIfQpnUF0ulVW6gSA\n" +
                "hx5ggDh5Cqr1/LZbQ36iwRLCCGQtwbnnBPf3Vjm3NyjgpdbnJkaG1VFMeBLK5YhGYZCqgPrtjick\n" +
                "AZHOrVGKWZFKssm9sPxyT9D0iP15pZnuJSN0vIFHhqOO6irgKM8c8z15USnuWEsIhVuuW5vk9Nnt\n" +
                "SW3tbrVHX1ZCI7VOsiJlYgPOSRmI8iDWlTD04YZmX2OyeSZs9YNDD9YczSMZZm+9I/FvgOCjyUVm\n" +
                "2z3ybH6obI4PHarSWuYkiGCPGjZweqq2W/061EJbc/Q6nHOPqVN1sahntpwke/FJ653RgxlSCCOp\n" +
                "zjHauoWtRcWwuhGUlJIb6qOyq2l1VraDfRN+RmWONc4BdzgZPQDmfdXdcdz5OJycVwKL216/rSah\n" +
                "KsnaJUWNT2CkZYe8136sF1Hg6/hptZcuTpPc3GoaXe2sgzdQNuNugASGNhIpA5AsBjHerEo12Rku\n" +
                "mLNynCUX2jQPRhrKXNjEwIDYwy9VZRusOPmPkRWkZ430AZV6ZdgGuQL+0X9JjwXUftVXiCO7L+I4\n" +
                "dqAORY6LqN1mRg1wIkDoxYKMgPjGQGwW488HgcGgDO/SxszFZ3iSWyIItxG3R7LPvY3QM8SeeB0B\n" +
                "qH0Suyr9KbZu1JG6SmSO2eOPhyqjR9MZ1na+gnU4Jnra3LxOskbFHQ7ysDggjtUMEbfBdC/tUvFA\n" +
                "E4QG4QfaPIyKPfzFYWqgpSlKPa7X6mjVJpJPz1+xO2Wk4OvYgj48P8qx9QuUzQpfaL2ly8fq90eZ\n" +
                "En0tw/occ39xcRSH+He3WPwDE1XaswaLKZbbIv5lZo9yqMQxxnhn3d6xjesi2uD3XZ6MGVlY70sh\n" +
                "lbPHiVVcDyworuUnLHyKK3sb+ZAt4DdytY2+TCDi8n5ADrDGersOBI9kE9ab02n/AKpCmq1OfhR7\n" +
                "X0i3moiFABaaeAAoHqtcEcPeI1xjzNW6qzbHavJRpq8yy/Awk9azTQF6fakMSLaF7jBwXBVIwf8A\n" +
                "eSEBv8Oat9PH3ng4U3L7qyRzrWoc1tbZh90XXrf9SBc/GjFfu/7A1av6Qg23jRxFeQyWjscKZACj\n" +
                "HykTK/OunQ2sxeThXJPElgYL6yjnQK/FQyupB6oQykEe74iqk3FlrSZ4tpEe9nj7s8K5LvVZAia0\n" +
                "sTJlyZJpDIwALuxPLCICcAYA4Yq34p4+Qt8MM+7Fs2F8Lk3GnQPbq7b0qzvGsch6sEUl0Y9ximq7\n" +
                "1BYk8i1lLm8xWC9kOtPzu7aIdlR3x8WYZ+VS9ZHwiFo5e5Tyy6q0z239sIrCMOSIEAwxK4BzkHh+\n" +
                "NT/FcZ2kfw3OMlLb6JOy+AY7O7S3bc3kd4ZlZgGYrIT6xOcnjxNcOxJ7stN/idbHjbhPA06LsdZC\n" +
                "RZGErzRgNuTTGTwi3LgDunlwPHlVM77GsZ4LoUQTyZX6Wpc6i+OgA/E05pOIZFtX99L5CcKbQoGD\n" +
                "050N4WQRpHo6v3jgVlOGR2HHqDzBHUceVYesbhfuj7GjQt1eGPukqnil4+COp9X7jA5K+7t5Vm6u\n" +
                "MdqlHr/A5Q3nDLqkBsfq90eZF30i7v8AZl5vAEeA/A+7h+NAFFFpsbojkkbyqTg88gZ51iSXJuQs\n" +
                "ltJt+d2GQjhuxsR8FOKI9lcnw2cJfRadogniAwlsHX9+Rl4Fj1JY8TW2Y5V7D6YYLOMPxkkzLKer\n" +
                "PJ6zE/gPhWRdPfNs1aYbYJHjthIXMFoCQs7nxSDg+FGpd1z+9gL8aK+E5ewWfE1H3IbHkAAAOCqO\n" +
                "SgcgB0qpvPLNCMVFYRxUEnMqRyoYZ0EkTcGVunmvVSOeRXUZOLyiqyqM1hlbsvdSafejTJXL28oL\n" +
                "2jtxI4ZKE/MY93embErIeou/JnwbrnsfXg0KlRkpJ7yKNm8JF32OWbHM+Z5mp3NlkKV2yBNeSNzc\n" +
                "/A8PhXJcopFHPocchJleWTyMrqB5bqFRVita6S/sVumMnmTf9/2K47GWxn3zEpi8Pd3Szk7+9ne9\n" +
                "rPLhVn8RPbjPJV/CV7844x8znSti7RWlMsRwZCU3JZFKpwwODY+dTLUz4wcLRQ5+vH0LzZrYtoXk\n" +
                "mju5496Q7gDK4ManCBw4OTj4jNE71JJYRTGhwb5EDX9PeW4vL5h9TDlA3R5MYwPcc5+ApiuW2MYr\n" +
                "tlVkN05SfSRX7ObDTXCiSRvBjPLIyzDuB0Hmast1cYcLk5p0UrFl8ItNV9HvhRmaCVpGj9cowALB\n" +
                "eJ3SOtVQ1e/4ZeSy3QutbovODvDqCooniXML+tKo9qMnm2Oo7j40i6nJ7Jv4l17P5EqaS3R68jns\n" +
                "rcAyAqcqyk8OR6g1n35jFp/iNVcvKGqkBsfq90eaFT0qPjSbz/dEfMgUALV6xxGpPARpw+HGsSXZ\n" +
                "6ChfCW9tl7Yg8SUYfgRRHsqtXLE/X7wyaDpMJOfHlgibzweP5VszeItmNBZkkaBjHAVimuLm2Fu4\n" +
                "MF0il/o7kyIoyzRSKUk3R1IB3sdd2ramnmL8ldmU1JeCD2I5EAgkEZB5cDVTWODQjJSWUdd8ZxkZ\n" +
                "7Z4/KoJydsUAR9qdPd7rSgoO+shLHHsqoDNk9OVM1SShIzb1myOPcYtbvcfVqcfe8vKlxuuPllKB\n" +
                "UF4f1+VSAVAAtAAKALXQZ8EoevEfy/zqSm2PGSq2zs40ggto4lfem3wjNuoxTLsZCASQSc46nFXV\n" +
                "yw3JsUnDdiKQvPtSI1cXELrMgz4UY3t5R9tW5BOmT8qlUbn8L4LZarYsSXPt+pP0fVJ1miFzbqsU\n" +
                "rbgKybxQt7IkGMYPLhmo9ODztfKCVtqXxRwmJWj2OJbqNDumC4dU7bu8QVYdVOKnVzxtb6a5/f6i\n" +
                "dUe0vDG/ZC0EcoCjC4J3ei55geVZWrm5Ry//AEcojiXA6Vnjg/V7o8yJ/pbXOlXKj7QUfNgKhgij\n" +
                "1kYkx2UD5ViM9FUvhLPROMWPMihFVv3hBv1I0PTpelrfet5BJnT+VbL5h+Birif4mnZrGNYKCRf2\n" +
                "wn3IiwHrKjMO/qqSKmKzJI7i2ot/IzB7Jfo4nj4TBPEEo9pmA3jvHqDyxyqxWy9XbLrOMDstLVLT\n" +
                "b4L4sZz59/zNE2SulmEchAO+gYeRxmq5R2yaFXLdWpLyNR79qgpFOeXeZm7n+QqBtLCPL+ulSBz/\n" +
                "AF/PnUEhUgFABUASdOP1qe+g5n91nO04BvNPVuTNKvzjH8qugswl+AjKTjOLXzE/0kp4V5bwx8rm\n" +
                "PwH8w0yE5/Kr9Ovgb9ufyKNTJua+fBebfKlrFBJkn9JQj3IGc/8Abj41VRHLf0L9Tb8K+omQ2jQJ\n" +
                "Fdszb11IDKhxjM2WBAxwIyPgTRf/ADMx/wCK4/ArVeyKn79/iPGzEXrO3YAfP/SsTUPhIboXLYw0\n" +
                "qMj9XujzIp+lWFm0u53Rkqof/gYMfyoYIotZTJWUcVdQc/CsNnoaZJrAaPebjbh9lj8jUE2QyslB\n" +
                "trE9taz27DfsZ94g4G9azM2+rEj2o2fnniMmtLTXbltfZj6ina9yLzYDWxdWUbk/WJ9XIOzJw/EY\n" +
                "PxpS+GybQ1TPfDIx1SXFHtGmSueRBB/zoLqumZXp8u7ZTdVTxVT+FSd3+Xwq6yOb188DOllt0Mm+\n" +
                "lux9OR02DBWC0HXcX8RXFv8A+j+opUv5C+g8XHsN7j+VcHC7FKoGw/rrQAUAH9dffQAUAFAEqxbd\n" +
                "LSkEiNS5A64HACpSy8Fd0sRF3VNTuJ57GWRI4lWUlXWQsPrIyFDZUYJ5Z5Z4UxHbtko9icoyUoyn\n" +
                "0RtttOuDdWlwyuywMS5UByoyGBwpLcx2qaJYhKOeWGognOMkuF2UW32uC+NrbxS72ZG3gRhlLYX1\n" +
                "gcEcM86t08HWpSkinVOFjjGBf+kDUITLaRDCxwgzSEjiNwBIx+JpeOXCTXb4LLvhkovpckvZPWT4\n" +
                "iwyQtH4ymSJiRlguMhh9k4OcVm6vS7Y70844ZfTY09rWM8ocKzRsfq90eZOsiBgVIBBGCDyIPMGg\n" +
                "DN7Vfoty2mzcUYF7Rz9qMc4yfvR8u5GKz9VTh7kP6W5/dfZPTR0DBsnAOcf+aTH/AFXjBOubdJEa\n" +
                "N1DIwIZSMgg8wRUptPKKmk1hmb22jTaNcvPEGlsJP1qjJeED7WPtBe44491Nuaujh/eFVB0yyujR\n" +
                "7a4SRFkRgyMMqwPAg9qUaaeGNJ5WURNaVCm6zAHmKgsrzkymPQbkQm03QEJI8beGNxmzwXnv44Yx\n" +
                "jPWr3KHqepn8PmWxlNaf+Hx8s+MfuaHs7ZgEY9mMAAfDAqhvLyRP4Y4QxmgoFjUbYo5HQ8RUDUJZ\n" +
                "RFoOgoAKACgDsiEkADJNAN4GCGBIYm8QgDHrsTgceHOul3wKWS3CpYWSXOjsqLlxE0akc96EkKR5\n" +
                "+qDV7e23P+8i6blTt/3gs9AiW6topt/6woPEHPdkAw4I5g5zwqqyG2TQxTe3FZK/Xdlo5B9dErdp\n" +
                "F4MPcw4iiFkodM7lXXb2ufzKNdn4LJxdXsjXCgBYt8ZOPsgD7cgJ4Va7ZT+GCwL+lGHxTllrolJO\n" +
                "sDNqV79Su7uwQk5dQeJJ7u2BwHIVn3N3v0KOfd+P/Ed7tr9W3j2QWmi63qS/S4JVtIW4RxsSCVHJ\n" +
                "zhTzz+FadH2bTCGJLL9xCzWWSllPCNyrRFQoAXNutnDeW+IzuXER8S3k5bsi8hns3I1DSawyU2nl\n" +
                "C/sprwu4SWXcmjO5NGecbjn8DzBrJtrdcsGpVYpxyXVVFpwyggg8QRg/Gggwyx16602W5so29Uym\n" +
                "NA3Hwi59V0z5HivLIzWi4RsxJ+woswg8Pzj9maNMpB3SSd31ck5Jx1JPzrOby8mtXHEUjpUHR1l1\n" +
                "b6KvjEbw3kTHcyOF/DOfhXdcN0sFGokowyOdclR43Vurrun4HtQTGTTyL13YPGeIyO4qBmM0yLQd\n" +
                "AKAJcNg7DJ9VerNwAqcHErIoiw7QwiQ29jG15c9fDxuJ5vIfVUe7JpivTTly+BC7Vx6I+0ey2rsV\n" +
                "llVLlcZ8GF90RN5K+BKR94nPYCr7NI3HEHgUWoy8yRX7MXU0ck1o0csW/wDXxq6FTwOJlHQ8SG4E\n" +
                "82peymca1u8cfsN6a2Dm0SgHtruG5TO5PIIJ144YspKORy3hu43vOog98HF+OUWXxUJqUfPYx6nq\n" +
                "Z3sRngOfDn8+lUDMK+ORc1Jb26vHNraCYQBY43kYCKGQrvSMATxbDKM9MEU0tI7YJbsJ948mbZeo\n" +
                "TfGWui52c9Fm9KLrVZRdTc1j4+Enlg+0PLAHkaepphVHbBYE52Sm8yZpoGOAq04OaACgAoAzrbzZ\n" +
                "6a3n/tWyTecDF1Cv7dB9oAc3UfP867a1ZHDLK7HCWUWGj6pFcwpPC28jjI8u4I6EdqyZRcXhmpGS\n" +
                "ksomVydGEelS2K6lLu8C6RyD3+zn5itGh/As/MVlHc5RXyf54G/S9qIJ1HiOsU2AHjkOMkc2Qngy\n" +
                "nn3pSdMk8x5Q7C7Z8FnD+ZOuNSt4xvSXESDzcEn3Bck1wqpvpHUtTXHyUx1iKeeF5Y5ksom8RWMT\n" +
                "E3DjgrMoGVQcxwNXqGxNJ/E/yE52O1qTXwoc49ttOP8AtcQPZjun5NiqvRn7HXrQ9z3/APVlh/7u\n" +
                "H/mLUelP2J9WHuRp9utNXndxH3HP5VKosfgh3QXkor30maYPYSSY/uQ4z8XxVi0s/PBC1SfEcv6F\n" +
                "He+ki5YEWtkkXZpCCfkMV0qK195lqr1VnUf7kDQNUhupmGtT3RA4hU/UAHl4nh8V48M4x3NN1xrS\n" +
                "+Ez9RC6Etthv2zVvZpAv0JYhCeKmLGD5kjmfM8auFi1oAQtp50l1W2iT27aKSWVh0EuERD7zk48q\n" +
                "W1bxDAxpVmeSzZAeYzWYaIva5DHbQvOctuj1U4esxOEXzyxArquG+SijuzUOMWxz2T0f6LapETvS\n" +
                "cXlb78jnekb/AIifhitlJJYRiNtvLLipICgAoAKACgAoAzfaLQ202aS/tlzav613Av2O88Q5cBxZ\n" +
                "ewzVF9PqL5l1Nux/ItG1BDD48Z8RCu+u6R64xwwfOstrDwzTXxdGL+kTUI7m9imiJKvarz5giVwV\n" +
                "YdCCMEU/VFxhh+/6FdPx3ce36kWaJW4MoI8xmq02uj0M64zWJJP6kZoBFuyxIokjYOvqjjunOD7x\n" +
                "XcZNvDfAjqtHX6TdcUmuejcNHvoL62jnChlcZweakcGHkQeFJzi4SwzOrnuWURb3ZK3k6fBlVh+N\n" +
                "QptdHbw+0Vjejy2P2If+Std+tP3Zz6df/FEi02CtVOSkf+GJB+NQ7Zvyw2QXUUZ7tJexyXrLAAIb\n" +
                "ceGuPtP+0bz+78KZScYLPb5GdBDfZKfhcL9To1tvIZU5L7a/dzyP8J/Cox5NRSxLa/w+f/0PRvfb\n" +
                "upRg8pUlhPnuneXPyFd2x/lv8GYV891kW/8Asv7M0+92VXeMtpLJZzE5LQnCsf8A5IvYf4iqK9TO\n" +
                "HzKbNPGXyPW02p1K0BF7ALqMZxNaj1+A4b8Rxz7qfnTkNTCXyFJ6ecfmQtlrWSGKa8uR+kXTmVwe\n" +
                "ag/q4/LdHDHSk9RZvlx0h3TVYWPckabcu0wyxOefblS47OKUTjVB9I1GysxxCMbqX3RcIwfexz8B\n" +
                "Tujhy5GXq58KJpdPiQUAFABQAUAFABQBwwzwPKgDKbW0FnqNzp8YP0aWL6REMHdhZsh0XoBwDAdM\n" +
                "0lrILG4d0djUtpl+t2ATUJcciofHYvxI+YJ+NRGbdSNLSUpaqTXt/k71wbQUAW2wGv8A0K68CQ4t\n" +
                "7hvVPSOT+R5fKurI+pDK7Rgamr+Ht/6y6+TNlpIAoAUfSRtN9Et/DjP6RN6kY+6D7T/AfjV9Fe55\n" +
                "fSKrG+IR7fRllnb7iBeZ6nuTzNXSlueTd09KprUF/rJ1heNE4deY4EHkwPNWHUHtUJ4LJwU47WSr\n" +
                "fTkS6tb63z4But2Retu78CjH7pJ9VvMCrpLMH9Dzl7cZ7JdqT598my3Mu4pbsKzRiKy8FTHrhzxU\n" +
                "Y8jxqC51LweGqX4k3QucDic96CYQ29nfRAAXkYgBF59upJ+AqUjm54R7+iy1aY3GqOCDctuwg/Zg\n" +
                "jJC/8R4n4VsVQ2RSMKye+TZoFWHAUAFABQAUAFABQAUAYftdtObLaCUuGaBo4xJgElMqMOB5dQOY\n" +
                "+FU3wU44L9O5KWUs+4kaq/jXUs4yEaQbuRglEGFOPOl/uRUTc0lUp/zHxl/kl+53rg1goA8bu2WR\n" +
                "Cjcj+HnXUZOLyim+mN0HCRo3o02naVPodwfr4x6jH9qg6/xLyPzqq6C+/How9k6penPvw/dDVtBr\n" +
                "UVpC08p4DgFHN2PJVHUmqoQc3hEyltRidzcS3E73c/6x+AXpGvRV/rqe9NtpLbHo0NFpHD+ZZ95/\n" +
                "kida2/igqv6wDKj74HMD94cx3rlcjspbOX1/gh1BYe2k641lcxOMGOSQLKh9llOOY7rjIPTFX1c5\n" +
                "+hjfa0cQi/8At+huayRTIJInWSJ+TKcjzBx1pK2rY8+GKVW7vqimuNFPNGB8j/OqRtW+5COny5xu\n" +
                "Ggs3x9yNtFbSOkGmxHEt2+JGH7OJeMjfLC/GmtLXull+DO1lvGEanY2iQxpFGoVEUIoHQKMAVpmY\n" +
                "e9ABQAUAFABQAUAFABQB8/el6Pc1rP8AeWyN8iy//mqb18JqfZMsX490L2c8+fT3dqVPR7cPJ1qD\n" +
                "o6ySBeZxUnMpKPZFe/HQZ99TtKZaheEeaak6srr6rId5SOakdQanaL22b44klgsk1S91OXe3BLIi\n" +
                "Fwud1UXl6o4jeP41xbKuiPxPCzj8RCnUxrxLbmT576RHtbkOOHAjgVPMEcCK6awbGn1EbobokiNy\n" +
                "pDKSCDkEcwRyIrkuaTWGXd9AtxE11GAJEx9IjHnw8VR2PUdCa7aysoWhJ1T9KXT+6/0/YXLa3zd2\n" +
                "YH2rqMn4MP5VbQ8szvteCjBNeX+h9H3ey1u3ilAYWl9sxErluj4HDfH3ufemHFNYZhJtcoTZNn9c\n" +
                "tj9VPBexjpKpjfHvGRml5aWD64L46ma7O8Ou3agifS7pGH93uSqf4SCDx91Ly0cvDL46uPlFpsHo\n" +
                "s3jTahdR+HLKBHFGcEwwqcgHH2mOCfcKdqr2RwKWT3yyO1WFYUAFABQAUAFABQAUAFAGJ+nqEC9s\n" +
                "JB7TJIpPkpUj/uNV2/cY79nPGpj/AL4Eekj1h5XTkKSKlFdrcYtoqia7EW8hQQdZOR91Suzmf3X9\n" +
                "DppWtTWZWaBsM0ZQ5GQR7u45g1N2nhqE4WLhPJk2fBCE120cWJO6Gycn1ieuTzon3g0dGttUWvqX\n" +
                "ls5Kgmqma9cnKOWWGkXzwzLIh4g448iG4EEdQe1CeGRbWrIOMiynsUj1u2iUYQXK4HbK72PdmmKl\n" +
                "ibMT7Qm56WuUu8n0NTBihQAUAFABQAUAFABQB//Z\n" +
                "/Users/garen/workspace/minlia/minlia-modules/module-attachment/static/下载1.jpeg\n" +
                "\n" +
                "Process finished with exit code 0\n",
            "contentType": "string",
            "originalFilename": "string"
        }
    }

}
