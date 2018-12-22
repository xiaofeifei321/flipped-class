package online.templab.flippedclass.dao.impl;

import online.templab.flippedclass.dao.CourseDao;
import online.templab.flippedclass.entity.*;
import online.templab.flippedclass.mapper.*;
import online.templab.flippedclass.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Component
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private KlassStudentMapper klassStudentMapper;

    @Autowired
    private KlassMapper klassMapper;

    @Autowired
    private KlassRoundMapper klassRoundMapper;

    @Autowired
    private KlassSeminarMapper klassSeminarMapper;

    @Autowired
    private ShareSeminarApplicationMapper shareSeminarApplicationMapper;

    @Autowired
    private  ShareTeamApplicationMapper shareTeamApplicationMapper;

    @Autowired
    private ConflictCourseStrategyMapper conflictCourseStrategyMapper;

    @Override
    public int insert(Course course) {
        return courseMapper.insertSelective(course);
    }

    @Override
    public int update(Course course) {
        return courseMapper.updateByPrimaryKeySelective(course);
    }

    @Override
    public Boolean delete(Long id) {
        //删除和klass相关
        List<Long> klasseIds=klassMapper.selectIdByCourseId(id);
        for(Long klassId:klasseIds)
        {
            klassStudentMapper.delete(new KlassStudent().setKlassId(klassId));
            klassRoundMapper.delete(new KlassRound().setKlassId(klassId));
            klassSeminarMapper.delete(new KlassSeminar().setKlassId(klassId));
            klassMapper.deleteByPrimaryKey(klassId);
        }
        //删除和共享相关
        System.out.println(id);
        Example exampleSeminar=new Example(ShareSeminarApplication.class);
        exampleSeminar.createCriteria().andCondition("main_course_id =",id);
        exampleSeminar.or().andCondition("sub_course_id =",id);
        shareSeminarApplicationMapper.deleteByExample(exampleSeminar);
        Example exampleTeam=new Example(ShareTeamApplication.class);
        exampleTeam.createCriteria().andCondition("main_course_id =",id);
        exampleTeam.or().andCondition("sub_course_id =",id);
        shareTeamApplicationMapper.deleteByExample(exampleTeam);
        //删除和strategy相关
        Example exampleConflict=new Example(ConflictCourseStrategy.class);
        exampleConflict.createCriteria().andCondition("course_1_id =",id);
        exampleConflict.or().andCondition("course_2_id =",id);
        conflictCourseStrategyMapper.deleteByExample(exampleConflict);
        //删除课程
        int line = courseMapper.deleteByPrimaryKey(id);
        return line == 1;
    }

    @Override
    public Course selectShareTeamMainCourseByPrimaryKey(Long id){
        // 如果传入 id 是从课程id 直接通过从课程拿到主课程id， 再去拿主课程
        Course subCourse = courseMapper.selectByPrimaryKey(id);
        Long teamMainCourseId=subCourse.getTeamMainCourseId();
        if(teamMainCourseId != null){
            return courseMapper.selectByPrimaryKey(teamMainCourseId);
        }else{
            return subCourse;
        }
    }

    @Override
    public  Course selectShareSeminarMainCourseByPrimaryKey(Long id){
        // 如果传入 id 是从课程id 直接通过从课程拿到主课程id， 再去拿主课程
        Course subCourse = courseMapper.selectByPrimaryKey(id);
        Long seminarMainCourseId=subCourse.getSeminarMainCourseId();
        // 说明是从课程id
        if(seminarMainCourseId != null){
            return courseMapper.selectByPrimaryKey(seminarMainCourseId);
        }else{ // 否则是主课程 id
            return subCourse;
        }
    }

    @Override
    public List<Course> selectShareTeamSubCourse(Long id){
        List<Course> courseList = courseMapper.select(new Course().setTeamMainCourseId(id));
        // 说明这个id 是主课程id ， 返回它的所有从课程
        if(courseList.size()>0){
            return courseList;
        }else{ // 说明这个 id  是从课程id，返回它自己
            courseList.add(courseMapper.selectByPrimaryKey(id));
            return courseList;
        }
    }

    @Override
    public List<Course> selectShareSeminarSubCourse(Long id){
        List<Course> courseList = courseMapper.select(new Course().setSeminarMainCourseId(id));
        // 说明这个id 是主课程id ， 返回它的所有从课程
        if(courseList.size()>0){
            return courseList;
        }else{ // 说明这个 id  是从课程id，返回它自己
            courseList.add(courseMapper.selectByPrimaryKey(id));
            return courseList;
        }
    }

    @Override
    public Course selectOne(Long id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Course> selectByTeacherId(Long teacherId) {
        return courseMapper.select(new Course().setTeacherId(teacherId));
    }

    @Override
    public List<Course> selectByStudentId(Long studentId) {
        return courseMapper.selectByStudentId(studentId);
    }

    @Override
    public List<Course> selectCourseKlassByStudentId(Long studentId) {
        return courseMapper.selectCourseKlassByStudentId(studentId);
    }
}
