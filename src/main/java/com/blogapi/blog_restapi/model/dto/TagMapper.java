package com.blogapi.blog_restapi.model.dto;


import com.blogapi.blog_restapi.model.pojo.Tag;

public interface TagMapper {

        TagDto toTagDto(Tag tag);
        Tag toTag(TagDto tagDto);
}
