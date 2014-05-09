//
//  AdfitYoutube.h
//  AdfitYoutube
//
//  Created by Kittipong Kulapruk on 5/8/2557 BE.
//  Copyright (c) 2557 Kittipong Kulapruk. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>
#import "AdfitYoutubeViewController.h"

@interface AdfitYoutube : CDVPlugin {
	AdfitYoutubeViewController * adfit;
}

@property (nonatomic, copy) NSString *callbackId;
@property (nonatomic, copy) NSString *notificationCallbackId;
@property (nonatomic, copy) NSString *callback;

- (void) openYoutube:(CDVInvokedUrlCommand*)command;
- (void) closeYoutubeWithFinish:(BOOL) finish;
@end