package com.minlia.module.article.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.article.entity.ArticleCategory;
import com.minlia.module.article.mapper.ArticleCategoryMapper;
import com.minlia.module.article.ro.ArticleCategoryCRO;
import com.minlia.module.article.ro.ArticleCategoryQRO;
import com.minlia.module.article.ro.ArticleCategoryURO;
import com.minlia.module.article.service.ArticleCategoryService;
import com.minlia.module.article.vo.ArticleSimpleVO;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by user on 2018/09/27.
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public ArticleCategory create(ArticleCategoryCRO cro) {
        if (null != cro.getParentId()) {
            ApiAssert.state(articleCategoryMapper.countByAll(ArticleCategoryQRO.builder().id(cro.getParentId()).delFlag(false).build()) > 0, SystemCode.Message.DATA_NOT_EXISTS);
            articleCategoryMapper.updateByPrimaryKeySelective(ArticleCategory.builder().id(cro.getParentId()).isLeaf(false).build());
        }
        ApiAssert.state(articleCategoryMapper.countByAll(ArticleCategoryQRO.builder().parentId(cro.getParentId()).code(cro.getCode()).locale(cro.getLocale()).delFlag(false).build()) == 0, SystemCode.Message.DATA_ALREADY_EXISTS);
        ArticleCategory articleCategory = mapper.map(cro, ArticleCategory.class);
        articleCategoryMapper.insertSelective(articleCategory);
        return articleCategory;
    }

    @Override
    public ArticleCategory update(ArticleCategoryURO uto) {
        if (null != uto.getParentId()) {
            ApiAssert.state(articleCategoryMapper.countByAll(ArticleCategoryQRO.builder().id(uto.getParentId()).delFlag(false).build()) > 0, SystemCode.Message.DATA_NOT_EXISTS);
            articleCategoryMapper.updateByPrimaryKeySelective(ArticleCategory.builder().id(uto.getParentId()).isLeaf(false).build());
        }
        ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(uto.getId());
        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, articleCategory);
        articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
        return articleCategory;
    }

    @Override
    public boolean disable(Long id) {
        ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(id);
        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);
        articleCategory.setDisFlag(!articleCategory.getDisFlag());
        articleCategoryMapper.updateByPrimaryKeySelective(articleCategory);
        return articleCategory.getDisFlag();
    }

    @Override
    public void delete(Long id) {
//        ArticleCategory articleCategory = articleCategoryMapper.selectByPrimaryKey(id);
//        ApiAssert.notNull(articleCategory, SystemCode.Message.DATA_NOT_EXISTS);

        //判断是否有子项
//        long count = articleService.count(ArticleQRO.builder().categoryId(id).build());
//        ApiAssert.state(count == 0, "存在广告无法删除");
//        articleCategoryMapper.deleteByPrimaryKey(articleCategory.getId());

        //判断是否有子项
        long count = articleCategoryMapper.countByAll(ArticleCategoryQRO.builder().parentId(id).delFlag(false).build());
        articleCategoryMapper.updateByPrimaryKeySelective(ArticleCategory.builder()
                .id(id)
                .delFlag(true)
                .isLeaf(count > 0 ? false : true)
                .build());
    }

    @Override
    public ArticleCategory selectByPrimaryKey(Long id) {
        return articleCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public long count(ArticleCategoryQRO qro) {
        return articleCategoryMapper.countByAll(qro);
    }

    @Override
    public ArticleCategory one(ArticleCategoryQRO qro) {
        List<ArticleCategory> list = articleCategoryMapper.selectByAll(qro);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<ArticleCategory> list(ArticleCategoryQRO qro) {
        return articleCategoryMapper.selectByAll(qro);
    }

    @Override
    public PageInfo<ArticleCategory> page(ArticleCategoryQRO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> articleCategoryMapper.selectByAll(qro));
    }

    @Override
    public List<ArticleSimpleVO> queryArticleByCategoryId(Long id) {
        return articleCategoryMapper.queryArticleByCategoryId(id);
    }

    @Override
    public List<Long> selectCategoryIdsById(Long id) {
        List<Long> categoryIds = Lists.newArrayList();
        categoryIds.add(id);
        do {
            Long parentId = articleCategoryMapper.selectParentIdById(id);
            id = parentId;
            if (null != parentId) {
                categoryIds.add(parentId);
            }
        } while (null != id);
//        categoryIds.sort(Comparator.reverseOrder());  //倒序
//        categoryIds.sort(Comparator.naturalOrder());  //正序
        Collections.reverse(categoryIds);
        return categoryIds;
    }

    public static void main(String[] args) {
        List<Long> categoryIds = Lists.newArrayList();
        categoryIds.add(3L);
        categoryIds.add(1L);
        categoryIds.add(2L);
//        categoryIds.sort(Comparator.reverseOrder());
        Collections.reverse(categoryIds);
        categoryIds.stream().forEach(System.out::println);
    }

}