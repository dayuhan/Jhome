package com.nettyService.service.impl;

import com.nettyService.dao.NsMessageInformationDao;
import com.nettyService.model.bo.NsMessageInformation;
import com.nettyService.service.NsMessageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: jhome-root
 * @description:
 * @author: Daxv
 * @create: 2020-09-12 16:43
 **/
@Service
public class NsMessageInformationSImpl implements NsMessageInformationService {
    @Autowired
    protected NsMessageInformationDao nsMessageInformationDao;

    @Override
    public boolean save(NsMessageInformation nsMessageInformation) {
        return nsMessageInformationDao.save(nsMessageInformation);
    }

    @Override
    public boolean update(NsMessageInformation nsMessageInformation) {
        return nsMessageInformationDao.update(nsMessageInformation);
    }

    @Override
    public boolean delete(String id) {
        return nsMessageInformationDao.delete(id);
    }

    @Override
    public List<NsMessageInformation> findAll() {
        return nsMessageInformationDao.findAll();
    }

    @Override
    public List<NsMessageInformation> getList(NsMessageInformation NsMessageInformation) {
        return nsMessageInformationDao.getList(NsMessageInformation);
    }
}
