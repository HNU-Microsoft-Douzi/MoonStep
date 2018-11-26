package priv.zxy.moonstep.wheel.animate;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述:AnimationEffect的工厂类，目的是封装对于Effect对象的选择，解耦业务逻辑和客户端间的联系
 *      不用简单工厂模式的原因是因为会破坏OCP原则，不用其他设计模式的原因是，情景不符合
 **/

public abstract class AnimateFactory {

    public abstract AnimateEffect createEffectObject();

}
