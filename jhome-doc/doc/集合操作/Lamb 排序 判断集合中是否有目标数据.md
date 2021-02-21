        //排序 取x降序
        List<PointC> pc3Start = pc3.stream().filter(c -> {
            return c.getY() >= 0;
        }).sorted(Comparator.comparing(PointC::getX).reversed()).collect(Collectors.toList());
        pc3Result.addAll(pc3Start);
        pc3Result.add(pc);
        //排序 取x升序
        List<PointC> pc3End = pc3.stream().filter(c -> {
            return c.getY() <= 0;
        }).sorted(Comparator.comparing(PointC::getX)).collect(Collectors.toList());
        pc3Result.addAll(pc3End);
        pc3Result.add(new PointC(pc3End.get(pc3End.size() - 1).getX(), 1, 0));
        
        
        
        //判断集合中是否存在匹配的点
        protected void addByPointC(List<PointC> pointCS, PointC pointC) {
                //判断是否存在点
                if (pointCS.stream().filter(t -> {
                    return t.getX() == pointC.getX() && t.getY() == pointC.getY();
                }).findFirst().isPresent())
                    return;
                if (pointCS.stream().filter(c -> {
                    return c.getX() == pointC.getX() && (pointC.getY() > 0 ? c.getY() > 0 : c.getY() < 0);
                }).findFirst().isPresent()) {
                    pointCS.stream().filter(c -> {
                        return c.getX() == pointC.getX() && (pointC.getY() > 0 ? c.getY() > 0 : c.getY() < 0);
                    }).collect(Collectors.toList()).forEach(c -> {
                        if (c.getX() == pointC.getX()) {
                            if (pointC.getY() < 0 && c.getY() < 0 && c.getY() > pointC.getY()) {
                                c.setY(pointC.getY());
                                c.setC(pointC.getC());
                            } else if (pointC.getY() > 0 && c.getY() > 0 && c.getY() < pointC.getY()) {
                                c.setY(pointC.getY());
                                c.setC(pointC.getC());
                            }
                        }
                    });
                } else if (!pointCS.stream().filter(c -> {
                    return c.getX() == pointC.getX() && c.getY() == pointC.getY();
                }).findFirst().isPresent()) {
                    pointCS.add(pointC);
                } else {
                    pointCS.add(pointC);
                }