/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ssanalytics.snapshotplugin.domainModel.daoReturnValues;

/**
 *
 * @author chw
 */
public class CategoryItem extends NamedItem{
    
    protected String cat;
    protected Long created_time;
    
    public CategoryItem(String id, String name, String category, Long created_time){
        super(id, name);
        this.cat = category;
        this.created_time = created_time;
    }
    
    
    public Long getCreated_time(){
        return this.created_time;
    }
    
    public String getCategory(){
        return this.cat;
    }
    
}
