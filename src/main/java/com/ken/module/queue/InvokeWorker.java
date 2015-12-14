package com.ken.module.queue;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ken.core.redis.Redis;
import com.ken.module.order.BackwardInvoke;


/**
 * 处理队列中的回调数据
 * @author YangJiang
 * @time 2015年9月19日 下午6:09:45
 */
public class InvokeWorker implements Runnable  {

	private static Logger logger = Logger.getLogger(InvokeWorker.class);
	
	private BackwardInvoke invoke;
	
	public InvokeWorker(BackwardInvoke invoke){
		this.invoke = invoke;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			try {
				List<String> datas = Redis.use(QueueDicKey.redis_order_name).blpop(QueueDicKey.INVOKE_DATA_LIST);
				logger.info("get invoke data "+datas+" from redis "+QueueDicKey.INVOKE_DATA_LIST);
				if (!datas.isEmpty()) {
//					BackwardInvoke invoke = Enhancer.enhance(BackwardInvoke.invoke);
					JSONObject json = JSONObject.parseObject(datas.get(1));
					String callbackUrl = json.getString("callbackUrl");
					json.remove("callbackUrl");
					invoke.invokeCustomerOrder(callbackUrl,json);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("线程处理队列中回调客户异常", e);
			}
		}
	}
}
