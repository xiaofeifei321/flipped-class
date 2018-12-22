package online.templab.flippedclass.mapper;

import online.templab.flippedclass.entity.Course;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wk
 */
@Component
public interface CourseMapper extends Mapper<Course> {

    /**
     * 根据 studentId 查找该 student 的所有 course
     *
     * @param studentId
     * @return
     */
    List<Course> selectByStudentId(Long studentId);

    /**
     * 根据 studentId 查找该 student 的所有 course
     * course注入对应 student 的班级
     *
     * @param studentId
     * @return
     */
    List<Course> selectCourseKlassByStudentId(Long studentId);

    /**
     * 根据 courseId 查找主课程及其所有从课程
     * 每个课程注入其下的所有 klass
     *
     * @param courseId
     * @return
     */
    Course selcetByCourseId(Long courseId);
}