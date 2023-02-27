package com.peels.eduOrder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.Jwt.JwtUtil;
import com.peels.commonUtils.Result.Result;
import com.peels.commonUtils.ThreadUtil.ThreadUtil;
import com.peels.eduOrder.client.CourseClient;
import com.peels.eduOrder.client.UCenterClient;
import com.peels.eduOrder.entity.TOrder;
import com.peels.eduOrder.mapper.TOrderMapper;
import com.peels.eduOrder.service.ITOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.models.EduModel.Dto.CourseInfoDto;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.Ucenter.UcenterMember;
import com.peels.serviceBase.systemConstants.EduConstant.PayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-27
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

    @Resource
    CourseClient courseClient;

    @Resource
    UCenterClient uCenterClient;

    @Override
    @Transactional
    public String createOrder(String id) {

        String memberId = (String) ThreadUtil.getValue();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest)requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(request);
        UcenterMember userInfoOrder = uCenterClient.getMemberFullInfoById(memberIdByJwtToken);

        CourseInfoDto courseInfoOrder = courseClient.getCourseInfoInClient(id);

        TOrder order = new TOrder();
        order.setOrderNo(RandomUtil.randomNumbers(16));

        order.setCourseId(id); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());

        order.setStatus(PayConstants.PAY_PENDING);
        order.setPayType(PayConstants.WX_PAY);

        this.save(order);
        return order.getOrderNo();
    }

    @Override
    public Boolean getCourseState(String courseId, String memberId) {
        LambdaQueryWrapper<TOrder>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TOrder::getCourseId,courseId);
        queryWrapper.eq(TOrder::getMemberId,memberId);
        queryWrapper.eq(TOrder::getStatus,PayConstants.PAY_RESOLVED);
        int count = this.count(queryWrapper);
        return count > 0;
    }
}
