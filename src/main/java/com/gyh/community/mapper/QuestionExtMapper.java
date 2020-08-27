package com.gyh.community.mapper;

import com.gyh.community.model.Question;
import com.gyh.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
   int incView(Question record);
   int incComment(Question record);
   List<Question> selectRelated(Question question);
}