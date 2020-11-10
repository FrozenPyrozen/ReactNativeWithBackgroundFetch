//
//  RCTCalendarManager.m
//  ReactNativeWithNativeModuleTest
//
//  Created by Kirill Kohan on 10.11.2020.
//

#import "RCTCalendarManager.h"
#import <React/RCTLog.h>

@implementation RCTCalendarManager

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
  RCTLogInfo(@"Pretending to create an event %@ at %@", name, location);
}

RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location date:(NSDate *)date)
{
  // Date is ready to use!
  RCTLogInfo(@"Pretending to create an event %@ at %@, date %@", name, location, date);
}

@end
