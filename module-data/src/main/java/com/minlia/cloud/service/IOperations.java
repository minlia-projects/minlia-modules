//package com.minlia.cloud.service;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Persistable;
//
//import java.io.Serializable;
//import java.util.Iterator;
//import java.util.List;
//
//public interface IOperations<ENTITY extends Persistable,PK extends Serializable> {
//
//    /**
//     * findOne
//     *
//     * @param id
//     * @return
//     */
//    public ENTITY findOne(final PK id);
//
//    /**
//     * - contract: if nothing is found, an empty list will be returned to the calling client <br>
//     */
//    public List<ENTITY> findAll();
//
//
//
//    /**
//     * is entity exists
//     * @param id
//     * @return
//     */
//    Boolean exists(PK id);
//
//    public Page<ENTITY> findAll(Pageable pageable);
//
//
//
//
//    // create
//    public ENTITY create(ENTITY resource);
//
//
//    // update
//    public ENTITY update(ENTITY resource);
//
//    // delete
//    public void delete(PK id);
//
//    /**
//     * deletion in batch
//     * @param ids
//     */
//    public void delete(Iterator<PK> ids);
//
//    /**
//     * delete all
//     */
//    public void deleteAll();
//
//    // count
//    public Long count();
//
//
////    /**
////     * @param body
////     * @return
////     */
////    public List<ENTITY> findListByBody(ApiQueryRequestBody body);
////    /**
////     * @param body
////     * @param pageable
////     * @return
////     */
////    public Page<ENTITY> findPageByBody(ApiQueryRequestBody body, Pageable pageable);
////
////
////
////
////
////
////
////
////
////
////
////
////
////
//////
////    @Transactional(readOnly = true)
////    public Page<ENTITY> findPage( SpecificationDetail<ENTITY> specificationDetail,Pageable pageable);
//////
//////
////    @Transactional(readOnly = true)
////    public Page<ENTITY> findBasePage( SpecificationDetail<ENTITY> specificationDetail, Pageable pageable,boolean isBasic) ;
////
////    /**
////     * 动态分页查询
////     *
////     * @param pm                  分页对象
////     * @param specificationDetail 动态条件对象
////     * @param isBasic             是否关联对象查询
////     * @param selectStatement     自定义数据集合sql名称
////     * @param countStatement      自定义数据总数sql名称
////     * @return
////     */
////    @Transactional(readOnly = true)
////    public Page<ENTITY> findBasePage(Pageable pageable, SpecificationDetail<ENTITY> specificationDetail, Boolean isBasic, String selectStatement, String countStatement);
//}
