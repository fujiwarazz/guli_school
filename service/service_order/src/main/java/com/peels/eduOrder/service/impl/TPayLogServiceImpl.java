package com.peels.eduOrder.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.eduOrder.client.CourseClient;
import com.peels.eduOrder.entity.TOrder;
import com.peels.eduOrder.entity.TPayLog;
import com.peels.eduOrder.mapper.TPayLogMapper;
import com.peels.eduOrder.service.ITOrderService;
import com.peels.eduOrder.service.ITPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.eduOrder.utils.HttpClient;
import com.peels.serviceBase.systemConstants.EduConstant.PayConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-27
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements ITPayLogService {

    @Resource
    private ITOrderService orderService;
    @Resource
    private CourseClient courseClient;
    @Override
    public Map<String, Object> createNative(String id) {

        try {
            //1 根据订单号查询订单信息
            LambdaQueryWrapper<TOrder> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TOrder::getOrderNo,id);
            TOrder order = orderService.getOne(wrapper);

            //2 使用map设置生成二维码需要参数
            Map<String,String> m = new HashMap<>();
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", id); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map<String,Object> map = new HashMap<>();
            map.put("out_trade_no", id);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址

            return map;
        }catch(Exception e) {
            throw new BusinessException(20001,"生成二维码失败");
        }

    }


    @Override
    public Map<String, String> getPayStatusById(String id) {
        try {
            //1、封装参数
            Map<String,String> m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", id);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();

            //4、转成Map再返回
            return WXPayUtil.xmlToMap(xml);
        }catch(Exception e) {
            return null;
        }

    }


    //TODO: 采用抽象工厂方式来优化支付方式的选择
    @Override
    public void updateOrderState(Map<String, String> result) {
        //从map获取订单号
        String orderNo = result.get("out_trade_no");

        //根据订单号查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        TOrder order = orderService.getOne(wrapper);

        //对同一笔订单上锁
        synchronized (orderNo.intern()){
        //更新订单表订单状态
        if(order.getStatus() == PayConstants.PAY_RESOLVED.intValue()) { return; }
        order.setStatus(PayConstants.PAY_RESOLVED);//1代表已经支付
        orderService.updateById(order);
        }
        String courseId = order.getCourseId();
        Boolean handlePurchase = courseClient.handlePurchase(courseId);
        if(!handlePurchase){
            throw new BusinessException(20002,"购买失败");
        }
        //向支付表添加支付记录
        TPayLog payLog = new TPayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(PayConstants.WX_PAY);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(result.get("trade_state"));//支付状态
        payLog.setTransactionId(result.get("transaction_id")); //流水号
        payLog.setAttr(JSON.toJSONString(result));

        this.save(payLog);
    }

}
