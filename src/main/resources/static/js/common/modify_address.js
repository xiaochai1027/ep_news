function initAddress(provinceId, cityId, areaId
    , provinceCode, cityCode, areaCode) {
    $('#' + provinceId + '').change(function(){
        var tmpProvinceCode = $('#' + provinceId + '>option:selected').data('code');
        $('#' + cityId + '').html('<option>城市</option>');
        $('#' + areaId + '').html('<option>地区</option>');

        getCity(cityId, tmpProvinceCode);
    });

    $('#' + cityId + '').change(function(){
        var tempCityCode = $('#' + cityId + '>option:selected').data('code');
        $('#' + areaId + '').html('<option>地区</option>');
        getArea(areaId, tempCityCode)
    });

    getProvince(provinceId, provinceCode, cityId, cityCode, areaId, areaCode);

    if (!provinceCode) {
        $('#' + cityId + '').html('<option>城市</option>');
        $('#' + areaId + '').html('<option>地区</option>');
    }
}

//获取省份
function getProvince(province, selectedProvinceCode,city,cityCode,area,areaCode) {
    //获取省份
    ajax({
        url : '/api/address/getProvinces'
    }).done(function(data){

        var temp = '<option>省份</option>';
        var results = data.results;
        $.each(results.list,function(i,item){
            temp += '<option';

            if (item.code == selectedProvinceCode) {
                temp += ' selected="selected"';
            }

            temp +=' data-code="' + item.code + '">' + item.name+ '</option>';
        });
        $('#' + province + '').html(temp);
        if ($('#j-pro-version')) {
            $('#j-pro-version').val(results.version);
        }
        if (selectedProvinceCode) {
            getCity(city, selectedProvinceCode, cityCode, area, areaCode);
        }
    },function(){});
}
//获取城市
function getCity(city, provinceCode, selectedCityCode, area, areaCode){
    //香港、台湾、澳门没有市县
    var version = $('#j-pro-version').val();
    if ((!provinceCode
        || provinceCode == '710000'
        || provinceCode == '810000'
        || provinceCode == '820000') && version == 0) {
        return;
    }

    ajax({
        url : '/api/address/getCities?provinceCode=' + provinceCode
    }).done(function(data){

        var temp = '<option>城市</option>';
        $.each(data.results, function(i, item){
            temp += '<option';
            if (item.code == selectedCityCode) {
                temp += ' selected="selected"'
            }

            temp += ' data-code="' + item.code + '">' + item.name+ '</option>';
        });
        $('#' + city + '').html(temp);

        if (selectedCityCode){
            getArea(area, selectedCityCode, areaCode);
        } else {
            $('#' + area + '').html('<option>地区</option>');
        }
    },function(){});

}
//获取区域
function getArea(area, cityCode, selectedAreaCode){
    if (!cityCode) {
        return;
    }

    ajax({
        url : '/api/address/getAreas?cityCode=' + cityCode
    }).done(function(data){

        var temp = '<option>地区</option>';
        $.each(data.results,function(i, item){
            temp += '<option';

            if (item.code == selectedAreaCode) {
                temp += ' selected="selected"';
            }
            temp += ' data-code="' + item.code + '">' + item.name+ '</option>';
        });
        $('#' + area + '').html(temp);
    },function(){});
}
