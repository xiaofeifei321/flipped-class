package online.templab.flippedclass.mapper;

import java.util.List;
import online.templab.flippedclass.entity.Klass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface KlassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table klass
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table klass
     *
     * @mbg.generated
     */
    int insert(Klass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table klass
     *
     * @mbg.generated
     */
    Klass selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table klass
     *
     * @mbg.generated
     */
    List<Klass> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table klass
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Klass record);
}