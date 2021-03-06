日常工具
======

### 简单介绍
  * android一些日常工具的封装、okhttp网络框架的二次封装、安全加密工具封装、布局控件等等. 目的是让开发者简单的快速的集成，减少不必要的工作量

### 使用依赖
  * ![](https://jitpack.io/v/natural1law/briefness.svg "")
  ```
    implementation 'com.github.natural1law.briefness:2.4.15'
  ```
  * 分支-网络请求
  ```
  implementation 'com.github.natural1law.briefness.network:2.4.15'
  ```
  * 分支-常用工具
  ```
  implementation 'com.github.natural1law.briefness.reduce:2.4.15'
  ```
  * 分支-图表统计
  ```
  implementation 'com.github.natural1law.briefness.echarts:2.4.15'
  ```
  * 分支-视图布局
  ```
  implementation 'com.github.natural1law.briefness.view:2.4.15'
  ```

### 工具使用
  * **暂不介绍，目前由作者自己使用，待项目稳定后在做介绍**

### 更新日志
  
  > 修订版本 2.4.15
  * @日期: 2021/07/12
  * @声明: 1.删除第三方AAChartCore，使用更轻量更便捷的AgentWeb+百度echarts（js）框架组合开发。2.代码优化。3.已知bug修复。4.删除获取动态权限，推荐使用5.新增excel导出
    ```
    implementation "com.github.permissions-dispatcher:permissionsdispatcher:4.8.0"
    annotationProcessor "com.github.permissions-dispatcher:permissionsdispatcher-processor:4.8.0"
    ```
    具体使用请搜索https://github.com/permissions-dispatcher/PermissionsDispatcher 很方便

  > 修订版本 2.3.9
  * @日期: 2021/06/18
  * @声明: 新增浮动菜单
  
  > 修订版本 2.1.2
  * @日期: 2021/03/08
  * @声明: 代码重构、新增html风格分页工具、更新http接口调用
 
  > 修订版本 1.3.5.1
  * @日期: 2021/02/22
  * @声明: 新增轻量缓存工具（MicroCache）
  
  > 修订版本 1.3.4.1
  * @日期: 2021/02/20
  * @声明: 新增倒计时器工具（CountDown）
  
  > 修订版本 1.3.3.2
  * @日期: 2021/02/18
  * @声明: http工具优化
 
  > 修订版本 1.3.2
  * @日期: 2021/01/28
  * @声明: 1.新增权限管理、新增SHA1加密方法、部分代码优化

  > 修订版本 1.2.9
  * @日期: 2020/12/10
  * @声明: 新增图表控件-折线图、曲线图、圆柱图、饼状图等等
  * @解释: 此功能是参考 [AAChartCore](https://github.com/AAChartModel/AAChartCore "AAChartCore")  项目,因此项目没有java版依赖，所以封装一套使用
