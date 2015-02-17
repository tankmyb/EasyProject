package com.java.threadSafe.field.v1;

import java.util.Random;

public class FieldValueWatcher extends Thread {  
  
    private String staticValue;  
  
    private String instanceValue;  
  
    public FieldValueWatcher() {  
        this.staticValue = "static--" + getName();  
        this.instanceValue = "instance--" + getName();  
    }  
  
    private FieldsVo vo;  
  
    public FieldsVo getVo() {  
        return vo;  
    }  
  
    public void setVo(FieldsVo vo) {  
        this.vo = vo;  
    }  
  
    public void run() {  
        System.out.println(getName() + "开始设置值[static value=" + this.staticValue  
                + ";instanceValue=" + this.instanceValue + "]");  
        FieldsVo.setStaticValue(this.staticValue);  
        vo.setValue(this.instanceValue);  
        System.out.println(getName() + " 去做別的事情，可能会花费比较久的时间");  
        Random r = new Random(2000);  
        try {  
            sleep(Math.abs(r.nextInt() % 10000));// 休眠时间的长短影响检测效果，时间越长，导致数据不完整的可能性越大  
        } catch (InterruptedException e) {  
            System.out.print(getName() + "被终止");  
        }  
        if (!this.staticValue.equals(FieldsVo.getStaticValue())) {  
            System.out.println(getName() + " FieldsVo的静态数据被打乱,现在的数据是:"  
                    + FieldsVo.getStaticValue());  
        } else {  
            System.out.println("幸运！静态数据完整：" + FieldsVo.getStaticValue());  
        }  
        if (!this.instanceValue.equals(vo.getValue())) {  
            System.out.println(getName() + " FieldsVo的实例数据被打乱,现在的数据是:"  
                    + vo.getValue());  
        } else {  
  
            System.out.println("幸运！实例数据完整：" + vo.getValue());  
        }  
    }  
    public static void main(String[] args) {  
        FieldValueWatcher[] ths = new FieldValueWatcher[] {  
                new FieldValueWatcher(), new FieldValueWatcher(),  
                new FieldValueWatcher(), new FieldValueWatcher() };  
        FieldsVo vo = new FieldsVo();  
        for (int i = 0, j = ths.length; i < j; i++) {  
            
            System.out.println(vo);  
            ths[i].setVo(vo);  
            ths[i].start();  
        }  
    }  

}
