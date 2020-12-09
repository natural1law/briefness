日常工具
======

### 简单介绍
  * android一些日常工具的封装、okhttp网络框架的二次封装、安全加密工具封装、布局控件等等
  * 目的是让开发者简单的快速的集成，减少不必要的工作量

### 依赖版本 
  ![](https://jitpack.io/v/natural1law/briefness.svg "依赖版本")
### 使用依赖
  * **implementation 'com.github.natural1law.briefness:tag'**
  
### 工具使用
  ```
    暂不介绍，目前由作者自己使用，待项目稳定后在做介绍
  ```

### 更新日志

  > 修订版本1.2.9
  * @日期：2020/12/08
  * @声明：修复项目混淆后图表加载失败的问题
  * @解释：项目使用时需添加混淆配置
   ```
   -keep public class com.androidx.chart.creator.*{*;}
   -keep public class com.androidx.chart.enums.*{*;}
   -keep public class com.androidx.chart.model.*{*;}
   -keep public class com.androidx.chart.tools.*{*;}
   ```
  
  > 修订版本1.2.4
  * @日期：2020/12/08
  * @声明：修复assets资源js无法加载的问题
  
  > 修订版本1.2.2
  * @日期：2020/12/08
  * @声明：修复assets资源不存在的问题
  
  > 修订版本1.2.1
  * @日期：2020/12/08
  * @声明：修复protobuf重复命名的问题

  > 修订版本1.2.0
  * @日期：2020/12/08
  * @声明：新增图表控件-折线图、曲线图、圆柱图、饼状图等等
  * @解释：此功能是参考 [AAChartCore](https://github.com/AAChartModel/AAChartCore "AAChartCore")  项目,因此项目没有java版依赖，所以封装一套使用

  > 修订版本1.1.1
  * @日期：2020/12/03
  * @声明：修复混淆后的BUG

  > 修订版本1.1.0
  * @日期：2020/12/02
  * @声明：日常使用工具、分组布局、网络请求封装等等
