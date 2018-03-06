package com.hl.AFCHelper.Until;

public class SearchDataHelper {
    public String ShowData(String tag) {
        switch (tag) {
            case "线路信息":
                return "select * from basic where id < 100";
            case "部门信息":
                return "select * from basic where id between 100 and 199";
            case "公司制度":
                return "select * from basic where id between 200 and 299";
            case "会议纪要":
                return "select * from basic where id between 300 and 399";
            case "下发文件":
                return "select * from basic where id between 400 and 499";
            case "培训内容":
                return "select * from basic where id between 500 and 599";
            case "工作相关":
                return "select * from basic where id between 600 and 699";
            case "应急预案":
                return "select * from basic where id between 700 and 799";

            case "theory_item_1":
                return "select * from theory where id < 100";
            case "theory_item_2":
                return "select * from theory where id between 100 and 199";
            case "theory_item_3":
                return "select * from theory where id between 200 and 299";
            case "theory_item_4":
                return "select * from theory where id between 300 and 399";
            case "theory_item_5":
                return "select * from theory where id between 400 and 499";
            case "theory_item_6":
                return "select * from theory where id between 500 and 599";

            case "repair_item_1":
                return "select * from repair where id < 100";
            case "repair_item_2":
                return "select * from repair where id between 100 and 199";
            case "repair_item_3":
                return "select * from repair where id between 200 and 299";
            case "repair_item_4":
                return "select * from repair where id between 300 and 399";
            case "repair_item_5":
                return "select * from repair where id between 400 and 499";
            case "repair_item_6":
                return "select * from repair where id between 500 and 599";

            case "radio_item_1":
                return "select * from radio where id < 100";
            case "radio_item_2":
                return "select * from radio where id between 100 and 199";
            case "radio_item_3":
                return "select * from radio where id between 200 and 299";
            case "radio_item_4":
                return "select * from radio where id between 300 and 399";
            case "radio_item_5":
                return "select * from radio where id between 400 and 499";
            case "radio_item_6":
                return "select * from radio where id between 500 and 599";

            case  "CHS代码查询":
                return "select * from chs_code";
            case  "BNR代码查询":
                return "select * from bnr_code";
            case  "MBC110代码查询":
                return "select * from mbc_code";
            case  "刷卡代码查询":
                return "select * from card_code";
            case  "车站IP查询":
                return "select * from ip_code";
            case  "螺丝查询":
                return "select * from screw_code";


            default:
                return "select * from basic where id between 100 and 200";
        }
    }

    public String SearchData(String tag) {
            switch (tag) {
                case "线路信息":
                    return "select * from basic where id < 100 and content like ?";
                case "部门信息":
                    return "select * from basic where id between 100 and 199 and content like ?";
                case "公司制度":
                    return "select * from basic where id between 200 and 299 and content like ?";
                case "会议纪要":
                    return "select * from basic where id between 300 and 399 and content like ?";
                case "下发文件":
                    return "select * from basic where id between 400 and 499 and content like ?";
                case "培训内容":
                    return "select * from basic where id between 500 and 599 and content like ?";
                case "工作相关":
                    return "select * from basic where id between 600 and 699 and content like ?";
                case "应急预案":
                    return "select * from basic where id between 700 and 799 and content like ?";

                case "theory_item_1":
                    return "select * from theory where id < 100 and content like ?";
                case "theory_item_2":
                    return "select * from theory where id between 100 and 199 and content like ?";
                case "theory_item_3":
                    return "select * from theory where id between 200 and 299 and content like ?";
                case "theory_item_4":
                    return "select * from theory where id between 300 and 399 and content like ?";
                case "theory_item_5":
                    return "select * from theory where id between 400 and 499 and content like ?";
                case "theory_item_6":
                    return "select * from theory where id between 500 and 599 and content like ?";

                case "repair_item_1":
                    return "select * from repair where id < 100 and content like ?";
                case "repair_item_2":
                    return "select * from repair where id between 100 and 199 and content like ?";
                case "repair_item_3":
                    return "select * from repair where id between 200 and 299 and content like ?";
                case "repair_item_4":
                    return "select * from repair where id between 300 and 399 and content like ?";
                case "repair_item_5":
                    return "select * from repair where id between 400 and 499 and content like ?";
                case "repair_item_6":
                    return "select * from repair where id between 500 and 599 and content like ?";

                case "radio_item_1":
                    return "select * from radio where id < 100 and content like ?";
                case "radio_item_2":
                    return "select * from radio where id between 100 and 199 and content like ?";
                case "radio_item_3":
                    return "select * from radio where id between 200 and 299 and content like ?";
                case "radio_item_4":
                    return "select * from radio where id between 300 and 399 and content like ?";
                case "radio_item_5":
                    return "select * from radio where id between 400 and 499 and content like ?";
                case "radio_item_6":
                    return "select * from radio where id between 500 and 599 and content like ?";

                case  "CHS代码查询":
                    return "select * from chs_code where content like ?";
                case  "BNR代码查询":
                    return "select * from bnr_code where content like ?";
                case  "MBC110代码查询":
                    return "select * from mbc_code where content like ?";
                case  "刷卡代码查询":
                    return "select * from card_code where content like ?";
                case  "车站IP查询":
                    return "select * from ip_code where content like ?";
                case  "螺丝查询":
                    return "select * from screw_code where content like ?";

                default:
                    return "select * from radio where id between 500 and 599 and content like ?";
            }
        }

}
